package io.ciera.maven;

import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.Arrays;

import org.apache.maven.plugin.AbstractMojo;

/**
 * Goal which runs the BridgePoint pre-builder.
 */
public abstract class AbstractPreBuildMojo extends AbstractMojo {

    @Parameter(defaultValue="${project.name}")
    protected String projectName;

    @Parameter(readonly=true, defaultValue="${project}")
    protected MavenProject project;

    @Parameter(defaultValue="${project.build.directory}/${project.name}.sql")
    protected String outputFile;

    protected void printCommand(ProcessBuilder pb) {
        getLog().debug(String.join(" ", pb.command().toArray(new String[0])));
    }

    protected boolean requiresBuild() {
        File outFile = new File(outputFile);
        if (!outFile.exists()) return true;
        else {
            return fileIsNewer(outFile, new File(project.getBasedir(), "models"));
        }
    }

    protected boolean fileIsNewer(File baseFile, File testFile) {
        boolean isNewer = false;
        if (testFile.exists()) {
            if (testFile.lastModified() > baseFile.lastModified()) isNewer |= true;
            else if (testFile.isDirectory()) {
                for (File child : Arrays.stream(testFile.list()).map((filename) -> new File(testFile, filename)).toArray(File[]::new)) {
                    isNewer |= fileIsNewer(baseFile, child);
                }
            }
        }
        return isNewer;
    }

}
