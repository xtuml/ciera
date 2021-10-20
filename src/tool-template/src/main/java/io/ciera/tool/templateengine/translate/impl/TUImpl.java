package io.ciera.tool.templateengine.translate.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.util.Utility;
import io.ciera.tool.TemplateEngine;
import io.ciera.tool.templateengine.architecture.expression.Literal;
import io.ciera.tool.templateengine.parser.RSLLexer;
import io.ciera.tool.templateengine.parser.RSLParser;
import io.ciera.tool.templateengine.parser.RSLPopulator;
import io.ciera.tool.templateengine.parser.RSLStringPopulator;
import io.ciera.tool.templateengine.translate.TU;

public class TUImpl<C extends IComponent<C>> extends Utility<C> implements TU {

    private String parseError;
    private String errPosition;

    public TUImpl(C context) {
        super(context);
        parseError = "";
    }

    @Override
    public void process_templates(final String p_template_path) {
        List<FileSystem> virtualFileSystems = new ArrayList<>();
        List<Path> templatePaths = new ArrayList<>();

        // Build a list of template root paths
        if (p_template_path != null && !p_template_path.isEmpty()) {
            for (String pathItem : p_template_path.split(":")) {
                Path pathItemPath = Paths.get(pathItem);
                ZipFile zipfile = null;
                FileSystem fs = null;
                try {
                    zipfile = new ZipFile(pathItemPath.toFile());
                } catch (IOException e) {
                    /* do nothing */ }
                if (zipfile != null) { // look for templates in zip file; assume that they are in a /templates folder
                    try {
                        Enumeration<? extends ZipEntry> entries = zipfile.entries();
                        while (entries.hasMoreElements()) {
                            ZipEntry entry = entries.nextElement();
                            if (entry.getName().endsWith("templates/")) {
                                if (fs == null) {
                                    fs = FileSystems.newFileSystem(new URI("jar:file:" + pathItemPath),
                                            new HashMap<>());
                                    virtualFileSystems.add(fs);
                                }
                                templatePaths.add(fs.getPath(entry.getName()));
                                break;
                            }
                        }
                    } catch (IOException | URISyntaxException e) {
                        context().getRunContext().getLog()
                                .warn("Failed to get templates from classpath item " + pathItemPath + ": " + e);
                    }
                } else { // look for templates in a local directory
                    templatePaths.add(pathItemPath);
                }
            }
        }

        // walk each template root path and parse all the templates
        for (Path startPath : templatePaths) {
            try {
                Files.walk(startPath).forEach(p -> {
                    try {
                        InputStream in = Files.newInputStream(p);
                        String templateName = startPath.relativize(p).toString();
                        context().getRunContext().getLog().info("Parsing: " + templateName);
                        processTemplate(templateName, in, (TemplateEngine) context());
                    } catch (IOException e) {
                        // Ignore directories
                    }
                });
            } catch (IOException e) {
                context().getRunContext().getLog().warn("Failed to get templates from path " + startPath + ": " + e);
            }
        }

        // close the virtual file systems for processing ZIP archives
        for (FileSystem fs : virtualFileSystems) {
            try {
                fs.close();
            } catch (IOException e) {
                context().getRunContext().getLog().error("Failed to close virtual file system: " + e);
            }
        }
    }

    @Override
    public void process_literal(final Literal p_expr) {
        try {
            parseError = "";
            final String input = "\"" + p_expr.getValue() + "\"";
            getRunContext().getLog().info("Processing string: " + input);
            RSLLexer lexer = new RSLLexer(CharStreams.fromString(input));
            lexer.pushMode(RSLLexer.CONTROL); // put the lexer in control mode
            lexer.removeErrorListeners();
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            RSLParser parser = new RSLParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
                        int charPositionInLine, String msg, RecognitionException e) {
                    parseError = msg;
                }
            });
            RSLParser.String_literalContext ctx = parser.string_literal();
            if ("".equals(parseError)) {
                ParseTreeWalker walker = new ParseTreeWalker();
                RSLStringPopulator listener = new RSLStringPopulator(p_expr.getParent_name(),
                        p_expr.getParent_package(), p_expr.getBody_name(), p_expr.getBlock_number(),
                        p_expr.getStatement_number(), p_expr.getExpression_number(), (TemplateEngine) context());
                walker.walk(listener, ctx);
            } else {
                getRunContext().getLog().warn("Syntax error: " + parseError);
            }
        } catch (XtumlException e) {
            getRunContext().getLog().error("Could not parse literal: ", e);
        }

    }

    private void processTemplate(String fileName, InputStream t, TemplateEngine population) throws IOException {
        parseError = "";
        RSLLexer lexer = new RSLLexer(CharStreams.fromStream(t));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RSLParser parser = new RSLParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
                    int charPositionInLine, String msg, RecognitionException e) {
                parseError = msg;
                errPosition = String.format("%d:%d", line, charPositionInLine);
            }
        });
        RSLParser.BodyContext ctx = parser.body();
        if ("".equals(parseError)) {
            ParseTreeWalker walker = new ParseTreeWalker();
            RSLPopulator listener = new RSLPopulator(fileName, population);
            walker.walk(listener, ctx);
        } else {
            getRunContext().getLog()
                    .warn(String.format("Syntax error in file %s %s: ", fileName, errPosition) + parseError);
        }

    }

}
