package io.ciera.tool.templateengine.translate.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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

    public TUImpl(C context) {
        super(context);
        parseError = "";
    }

    @Override
    public void process_templates(final String p_root_folder) {
        parse(p_root_folder, (TemplateEngine) context());
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

    private void parse(String filename, TemplateEngine population) {
        File template_dir = new File(filename);
        processFile(template_dir.getAbsoluteFile(), "", "", population);
    }

    private void processFile(File f, String basePath, String relativePath, TemplateEngine population) {
        if (null != f && f.exists() && null != basePath && null != relativePath) {
            population.LOG().LogInfo("  Processing file: " + f.getName());
            if (f.isDirectory()) {
                if ("".equals(basePath))
                    basePath = f.getAbsolutePath();
                else
                    relativePath = "".equals(relativePath) ? f.getName() : relativePath + File.separator + f.getName();
                for (File contained_file : f.listFiles())
                    processFile(contained_file, basePath, relativePath, population);
            } else {
                try {
                    RSLLexer lexer = new RSLLexer(CharStreams.fromStream(new FileInputStream(
                            basePath + File.separator + relativePath + File.separator + f.getName())));
                    /*
                     * lexer debug for ( Token token = lexer.nextToken(); token.getType() !=
                     * Token.EOF; token = lexer.nextToken() ) { System.err.println(
                     * lexer.getVocabulary().getSymbolicName(token.getType())); }
                     */
                    CommonTokenStream tokens = new CommonTokenStream(lexer);
                    RSLParser parser = new RSLParser(tokens);
                    RSLParser.BodyContext ctx = parser.body();
                    ParseTreeWalker walker = new ParseTreeWalker();
                    RSLPopulator listener = new RSLPopulator(relativePath + File.separator + f.getName(),
                            population);
                    walker.walk(listener, ctx);
                } catch (IOException e) {
                    /* do nothing */ }
            }
        }
    }

}
