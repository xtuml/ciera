package io.ciera.maven;

import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/** Goal which runs the pyxtuml pre-builder. */
@Mojo(name = "pyxtuml-pre-build", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class PyxtumlPreBuildMojo extends AbstractPreBuildMojo {

  @Parameter private String[] modelDirs;

  @Parameter(defaultValue = "true")
  private boolean includeLocalModel;

  @Parameter(defaultValue = "python")
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
  @Parameter(defaultValue = "")
  private String loggingLevel;

  public void execute() throws MojoExecutionException, MojoFailureException {
    String loggingArgument = loggingLevelToVerbosityParam();
    List<String> resources = new ArrayList<>();
    if (includeDependencyModels) {
      resources.addAll(
          getDependencyModels().stream()
              .map(
                  artifact ->
                      Paths.get(localRepository.getBasedir(), localRepository.pathOf(artifact))
                          .toString())
              .collect(Collectors.toList()));
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
      if (loggingArgument != "") {
        cmd.add(loggingArgument);
      }
      ProcessBuilder pb =
          new ProcessBuilder(cmd).redirectOutput(Redirect.PIPE).redirectError(Redirect.PIPE);
      getLog().info("Performing pyxtuml pre-build...");
      runProcess("pyxtuml pre-build", pb);
    } else {
      getLog().info("Pre-build output up to date.");
    }
  }

  private String loggingLevelToVerbosityParam() {
    switch (loggingLevel == null ? "" : loggingLevel) {
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
}
