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

    protected void printCommand(ProcessBuilder pb) {
        getLog().info(String.join(" ", pb.command().toArray(new String[0])));
    }

    protected boolean requiresBuild() {
        File outputFile = new File(project.getBasedir() + File.separator + "gen/code_generation" + File.separator + project.getName() + ".sql");
        if (!outputFile.exists()) return true;
        else {
            File modelsDir = new File(project.getBasedir() + File.separator + "models");
            return fileIsNewer(outputFile, modelsDir);
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
