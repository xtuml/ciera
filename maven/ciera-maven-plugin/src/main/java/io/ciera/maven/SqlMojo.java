package io.ciera.maven;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import io.ciera.runtime.summit.application.IApplication;
import io.ciera.tool.SqlTool;

/**
 * Goal which runs the SQL builder.
 */
@Mojo(name="sql", defaultPhase=LifecyclePhase.GENERATE_SOURCES)
public class SqlMojo extends AbstractCieraMojo {

    protected IApplication getTool() {
        return new SqlTool();
    }

}
