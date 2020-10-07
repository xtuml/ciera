package io.ciera.maven;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;

import java.lang.ProcessBuilder.Redirect;

/**
 * Goal which runs the pyxtuml pre-builder.
 */
@Mojo(name="pyxtuml-pre-build", defaultPhase=LifecyclePhase.GENERATE_SOURCES)
public class PyxtumlPreBuildMojo extends AbstractPreBuildMojo {

    @Parameter(defaultValue = "${localRepository}", readonly = true, required = true)
    private ArtifactRepository localRepository;

    @Parameter
    private String[] modelDirs;

    @Parameter(defaultValue="true")
    private boolean includeDependencyModels;

    @Parameter(defaultValue="true")
    private boolean includeLocalModel;

    @Parameter(defaultValue="python")
    private String pythonExecutable;

    // pyxtuml-prebuild available logging levels
    // 0: logging.ERROR,
    // 1: logging.WARNING,
    // 2: logging.INFO,
    // 3: logging.DEBUG,
    // python -v match
    // None 0
    // -v 1
    // -vv 2
    // -vvv 3
    @Parameter(defaultValue="")
    private String loggingLevel;

    public void execute() throws MojoExecutionException, MojoFailureException {
        String loggingArgument = loggingLevelToVerbosityParam();
        List<String> resources = new ArrayList<>();
        if (includeDependencyModels) {
            resources.addAll(getDependencyModels());
        }
        if (includeLocalModel) {
            resources.add(new File(project.getBasedir(), "models").getPath());
        }
        if (null != modelDirs) {
            resources.addAll(Arrays.asList(modelDirs));
        }
        if (requiresBuild()) {
            new File(project.getBuild().getDirectory()).mkdirs();
            List<String> cmd = new ArrayList<>();
            cmd.add(pythonExecutable);
            cmd.add("-m");
            cmd.add("bridgepoint.prebuild");
            cmd.add("-o");
            cmd.add(outputFile);
            cmd.addAll(resources);
            if(loggingArgument != "") {
                cmd.add(loggingArgument);
            }
            ProcessBuilder pb = new ProcessBuilder(cmd).redirectOutput(Redirect.PIPE).redirectError(Redirect.PIPE);
            getLog().info("Performing pyxtuml pre-build...");
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
                if (proc.exitValue() == 0) {
                    int duration = (int)(System.currentTimeMillis() - startTime);
                    int mins = duration / 60000;
                    int secs = (duration % 60000) / 1000;
                    int msecs = duration % 1000;
                    getLog().info(String.format("Pre-build duration: %d:%d.%03d", mins, secs, msecs));
                }
                else {
                    getLog().error(String.format("pyxtuml exited with code %d", proc.exitValue()));
                    throw new MojoFailureException(String.format("pyxtuml exited with code %d", proc.exitValue()));
                }
            } catch (IOException e) {
                getLog().error("Problem executing pre-builder:", e);
                throw new MojoFailureException("Problem executing pre-builder", e);
            }
        }
        else {
            getLog().info("Pre-build output up to date.");
        }
    }

    private String loggingLevelToVerbosityParam() {
        switch (loggingLevel) {
            case "WARN":
                return "-v";
            case "INFO":
                return "-vv";
            case "DEBUG":
                return "-vvv";
            default:
                return "";
        }
    }

    private List<String> getDependencyModels() {
        List<String> dependencyModels = new ArrayList<>();
        for (Artifact artifact : project.getDependencyArtifacts()) {
            Path artifactPath = Paths.get(localRepository.getBasedir(), localRepository.pathOf(artifact));
            ZipFile zipfile = null;
            try {
                zipfile = new ZipFile(artifactPath.toFile());
            } catch (IOException e) { /* do nothing */ }
            if (null != zipfile) {
                Enumeration<? extends ZipEntry> entries = zipfile.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    if (entry.getName().endsWith(".xtuml")) {
                        dependencyModels.add(artifactPath.toString());
                        break;
                    }
                }
            }
        }
        return dependencyModels;
    }

}
