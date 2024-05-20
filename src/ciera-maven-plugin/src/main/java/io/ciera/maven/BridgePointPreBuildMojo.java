package io.ciera.maven;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import java.lang.ProcessBuilder.Redirect;

/**
 * Goal which runs the BridgePoint pre-builder.
 */
@Mojo(name="bridgepoint-pre-build", defaultPhase=LifecyclePhase.GENERATE_SOURCES)
public class BridgePointPreBuildMojo extends AbstractPreBuildMojo {

    private static final String BIN_DIR = "tools/mc/bin";

    @Parameter(defaultValue="")
    private String workspace;

    @Parameter(defaultValue="")
    private String bpHome;

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (requiresBuild()) {
            final String workspace = null == this.workspace || "".equals(this.workspace) ? System.getenv("WORKSPACE") : this.workspace;
            final String bpHome = null == this.bpHome || "".equals(this.bpHome) ? System.getenv("BPHOME") : this.bpHome;
            final String cliExe = bpHome + File.separator + "bridgepoint";
            if (workspace == null || "".equals(workspace.trim()) || bpHome == null || "".equals(bpHome.trim())) {
                if (bpHome == null || "".equals(bpHome.trim())) {
                    getLog().error("BPHOME is unset.");
                }
                if (workspace == null || "".equals(workspace.trim())) {
                    getLog().error("WORKSPACE is unset.");
                }
                throw new MojoFailureException("Could not execute pre-build.");
            }
            else {
                ProcessBuilder pb = new ProcessBuilder(cliExe, "--launcher.suppressErrors", "-clean", "-noSplash", "-data", workspace, "-application", "org.xtuml.bp.cli.Build", "-project", projectName, "-prebuildOnly").redirectOutput(Redirect.PIPE).redirectError(Redirect.PIPE);
                getLog().info("Performing BridgePoint pre-build (workspace location: " + workspace + ")...");
                getLog().info("");
                printCommand(pb);
                try {
                    long startTime = System.currentTimeMillis();
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
                    int duration = (int)(System.currentTimeMillis() - startTime);
                    int mins = duration / 60000;
                    int secs = (duration % 60000) / 1000;
                    int msecs = duration % 1000;
                    getLog().info("");
                    getLog().info(String.format("Pre-build duration: %d:%d.%03d", mins, secs, msecs));
                } catch (IOException e) {
                    getLog().error("Problem executing pre-builder:", e);
                }
            }
        }
        else {
            getLog().info("Pre-build output up to date.");
        }
    }

}
