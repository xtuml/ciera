package io.ciera.maven;

import io.ciera.runtime.summit.application.IApplication;
import io.ciera.tool.CoreTool;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Goal which runs the Core builder.
 */
@Mojo(name="core", defaultPhase=LifecyclePhase.GENERATE_SOURCES)
public class CoreMojo extends AbstractXtumlCieraMojo {

    protected IApplication getTool() {
        return new CoreTool();
    }

    protected List<String> getAdditionalArguments() {
        List<String> args = new ArrayList<>();
        args.add("--use-version");
        args.add(project.getVersion());
        return args;
    }

}
