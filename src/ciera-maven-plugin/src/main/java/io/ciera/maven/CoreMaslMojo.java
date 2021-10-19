package io.ciera.maven;

import io.ciera.runtime.summit.application.IApplication;
import io.ciera.tool.CoreMaslTool;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.xtuml.stratus.parser.MaslImportParser;

/**
 * Goal which runs the Core builder for MASL models.
 */
@Mojo(name = "core-masl", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class CoreMaslMojo extends AbstractCieraMojo {

    protected IApplication getTool() {
        return new CoreMaslTool();
    }

    protected void postInitialize(IApplication compiler) {
        ((CoreMaslTool) compiler).CoreMasl().LOAD().setDefaultLoader(new MaslImportParser());
    }

    protected List<String> getAdditionalArguments() {
        List<String> args = new ArrayList<>();
        args.add("--use-version");
        args.add(project.getVersion());
        return args;
    }

}
