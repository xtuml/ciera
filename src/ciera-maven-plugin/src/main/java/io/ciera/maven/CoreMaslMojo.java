package io.ciera.maven;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.xtuml.stratus.parser.MaslImportParser;

import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.exceptions.XtumlExitException;
import io.ciera.tool.CoreMaslTool;

/**
 * Goal which runs the Core builder for MASL models.
 */
@Mojo(name = "core-masl", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class CoreMaslMojo extends AbstractCieraMojo {
    
    @Parameter
    private String modFile;

    @Parameter
    private String prjFile;

    @Parameter
    private String[] domainPaths;

    @Override
    protected IApplication getTool() {
        return new CoreMaslTool();
    }

    @Override
    protected void postInitialize(IApplication compiler) {
        ((CoreMaslTool) compiler).CoreMasl().LOAD().setDefaultLoader(new MaslImportParser());
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        initProperties();
        IApplication compiler = getTool();
        List<String> args = new ArrayList<>();
        if (prjFile != null) {
            args.add("--prj");
            args.add(prjFile);
        } else if (modFile != null) {
            args.add("--mod");
            args.add(modFile);
        } else {
            throw new MojoFailureException("Must supply a model file to translate.");
        }
        if (domainPaths != null) {
            args.add("--domainpath");
            args.add(Stream.of(domainPaths).collect(Collectors.joining(":")));
        }
        args.add("--output");
        args.add(genDir);
        args.addAll(getAdditionalArguments());
        try {
            compiler.setup(args.toArray(new String[0]), new CieraMavenLogger(getLog()));
            compiler.initialize();
            this.postInitialize(compiler);
            compiler.start();
        } catch (XtumlExitException e) {
            throw new MojoExecutionException("Ciera compiler failure", e);
        }
        copyCustomCode();
        addSrcGen();
        refreshFiles();
 
        
    }

}
