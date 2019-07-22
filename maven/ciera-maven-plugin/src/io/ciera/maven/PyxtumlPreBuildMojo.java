package io.ciera.maven;

import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.lang.ProcessBuilder.Redirect;

/**
 * Goal which runs the pyxtuml pre-builder.
 */
@Mojo(name="pyxtuml-pre-build", defaultPhase=LifecyclePhase.GENERATE_SOURCES)
public class PyxtumlPreBuildMojo extends AbstractPreBuildMojo {

    @Parameter
    private String[] modelDirs;

    public void execute() throws MojoExecutionException {
        if (requiresBuild()) {
            File codeGen = new File(project.getBasedir(), "gen/code_generation");
            File outputFile = new File(codeGen, projectName + ".sql");
            codeGen.mkdirs();
            List<String> cmd = new ArrayList<>();
            cmd.add("python");
            cmd.add("-m");
            cmd.add("bridgepoint.prebuild");
            cmd.add("-o");
            cmd.add(outputFile.getAbsolutePath());
            for (String modelDir : modelDirs) {
                cmd.add(modelDir);
            }
            ProcessBuilder pb = new ProcessBuilder(cmd).redirectOutput(Redirect.PIPE).redirectError(Redirect.PIPE);
            getLog().info("Performing pyxtuml pre-build...");
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
        else {
            getLog().info("Pre-build output up to date.");
        }
    }

}
