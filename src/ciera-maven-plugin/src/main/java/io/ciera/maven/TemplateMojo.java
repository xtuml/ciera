package io.ciera.maven;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import io.ciera.runtime.summit.application.IApplication;
import io.ciera.tool.TemplateTool;

/**
 * Goal which runs the Template builder.
 */
@Mojo(name="template", defaultPhase=LifecyclePhase.GENERATE_SOURCES)
public class TemplateMojo extends AbstractCieraMojo {

    protected IApplication getTool() {
        return new TemplateTool();
    }

}
