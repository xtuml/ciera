package io.ciera.maven;

import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.lang.ProcessBuilder.Redirect;

import org.apache.maven.plugin.AbstractMojo;

/**
 * Goal which runs the BridgePoint pre-builder.
 */
@Mojo(name="pre-build", defaultPhase=LifecyclePhase.GENERATE_SOURCES)
public class BridgePointPreBuildMojo extends AbstractMojo {

    private static final String BIN_DIR = "tools/mc/bin";

    @Parameter(defaultValue="${project.name}")
    private String projectName;

    @Parameter(defaultValue="${project.basedir}/src-gen")
    private String genDir;

    @Parameter(defaultValue="")
    private String workspace;

    @Parameter(defaultValue="")
    private String bpHome;

    @Parameter(readonly=true, defaultValue="${project}")
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        if (requiresBuild()) {
            final String workspace = null == this.workspace || "".equals(this.workspace) ? System.getenv("WORKSPACE") : this.workspace;
            final String bpHome = null == this.bpHome || "".equals(this.bpHome) ? System.getenv("BPHOME") : this.bpHome;
            final String cliExe = bpHome + File.separator + BIN_DIR + File.separator + "CLI.sh";
            if ("".equals(workspace.trim())) {
                getLog().warn("WORKSPACE is unset.");
            }
            ProcessBuilder pb = new ProcessBuilder(cliExe, "Build", "-project", projectName, "-prebuildOnly").redirectOutput(Redirect.PIPE).redirectError(Redirect.PIPE);
            pb.environment().put("WORKSPACE", workspace);
            getLog().info("Performing BridgePoint pre-build (workspace location: " + workspace + ")...");
            getLog().info("");
            printCommand(pb);
            try {
                Process proc = pb.start();
                Scanner sc = new Scanner(proc.getInputStream());
                while (sc.hasNextLine()) {
                    getLog().info(sc.nextLine());
                }
                sc.close();
                sc = new Scanner(proc.getErrorStream());
                while (sc.hasNextLine()) {
                    getLog().error(sc.nextLine());
                }
                sc.close();
            } catch (IOException e) {
                getLog().error("Problem executing pre-builder:", e);
            }
        }
        else {
            getLog().info("Pre-build output up to date.");
        }
    }

    private void printCommand(ProcessBuilder pb) {
        getLog().info(String.join(" ", pb.command().toArray(new String[0])));
    }

    private boolean requiresBuild() {
        File outputFile = new File(project.getBasedir() + File.separator + "gen/code_generation" + File.separator + project.getName() + ".sql");
        if (!outputFile.exists()) return true;
        else {
            File modelsDir = new File(project.getBasedir() + File.separator + "models");
            return fileIsNewer(outputFile, modelsDir);
        }
    }

    private boolean fileIsNewer(File baseFile, File testFile) {
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
