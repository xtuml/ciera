package io.ciera.maven;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.aether.repository.LocalArtifactResult;

import net.lingala.zip4j.ZipFile;

/** Goal which runs the BridgePoint pre-builder. */
@Mojo(name = "bridgepoint-pre-build", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class BridgePointPreBuildMojo extends AbstractPreBuildMojo {

  private static final String BIN_DIR = "tools/mc/bin";

  @Parameter(defaultValue = "")
  private String workspace;

  @Parameter(defaultValue = "")
  private String bpHome;

  public void execute() throws MojoExecutionException, MojoFailureException {
    if (requiresBuild()) {
      String workspace =
          null == this.workspace || "".equals(this.workspace)
              ? System.getenv("WORKSPACE")
              : this.workspace;
      final String bpHome =
          null == this.bpHome || "".equals(this.bpHome) ? System.getenv("BPHOME") : this.bpHome;
      final String cliExe = bpHome + File.separator + BIN_DIR + File.separator + "CLI.sh";
      if (bpHome == null || "".equals(bpHome.trim())) {
        getLog().error("BPHOME is unset.");
        throw new MojoFailureException("Could not execute pre-build.");
      } else {
        if (workspace == null || "".equals(workspace.trim())) {
          getLog().info("WORKSPACE is unset. Creating workspace...");
          try {
            workspace = createWorkspace(Path.of(bpHome, "bridgepoint")).toString();
          } catch (IOException e) {
            throw new MojoFailureException("Failed to create workspace.", e);
          }
        }
        ProcessBuilder pb =
            new ProcessBuilder(cliExe, "Build", "-project", projectName, "-prebuildOnly")
                .redirectOutput(Redirect.PIPE)
                .redirectError(Redirect.PIPE);
        pb.environment().put("WORKSPACE", workspace);
        getLog()
            .info("Performing BridgePoint pre-build (workspace location: " + workspace + ")...");
        runProcess("BridgePoint pre-build", pb);
        try {
          Files.createDirectories(Path.of(outputFile).getParent());
          Files.copy(
              Path.of(
                  project.getBasedir().getPath(), "gen", "code_generation", projectName + ".sql"),
              Path.of(outputFile),
              StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
          throw new MojoFailureException("Could not copy pre-build output to build directory", e);
        }
      }
    } else {
      getLog().info("Pre-build output up to date.");
    }
  }

  private Path createWorkspace(final Path bpExe) throws IOException, MojoFailureException {
    // create a temporary directory
    final Path workspacePath = Files.createTempDirectory(null);

    final List<LocalArtifactResult> dependencies = getDependencyModels();
    if (!dependencies.isEmpty()) {

      final Path dependenciesPath = Files.createTempDirectory(null);
      getLog().info("Unzipping dependenecies to temporary location: " + dependenciesPath);

      // unzip all dependency models into the newly created workspace
      for (LocalArtifactResult dependencyModel : dependencies) {
        final Path dependencyPath =
            Files.createDirectory(
                dependenciesPath.resolve(
                    dependencyModel.getRequest().getArtifact().getArtifactId()));
        final ZipFile zipfile = new ZipFile(dependencyModel.getFile());
        zipfile.extractAll(dependencyPath.toString());
      }

      // import dependencies into the workspace
      final ProcessBuilder pb =
          new ProcessBuilder(
                  bpExe.toString(),
                  "--launcher.suppressErrors",
                  "-nosplash",
                  "-data",
                  workspacePath.toString(),
                  "-application",
                  "org.eclipse.cdt.managedbuilder.core.headlessbuild",
                  "-importAll",
                  dependenciesPath.toUri().toString())
              .redirectOutput(Redirect.PIPE)
              .redirectError(Redirect.PIPE);
      getLog().info("Importing dependencies (workspace location: " + workspacePath + ")...");
      runProcess("import dependencies", pb);
    }

    // import this project into the workspace
    final ProcessBuilder pb =
        new ProcessBuilder(
                bpExe.toString(),
                "--launcher.suppressErrors",
                "-nosplash",
                "-data",
                workspacePath.toString(),
                "-application",
                "org.eclipse.cdt.managedbuilder.core.headlessbuild",
                "-import",
                project.getBasedir().toURI().toString())
            .redirectOutput(Redirect.PIPE)
            .redirectError(Redirect.PIPE);
    getLog().info("Importing project (workspace location: " + workspacePath + ")...");
    runProcess("import project", pb);

    return workspacePath.toAbsolutePath();
  }
}
