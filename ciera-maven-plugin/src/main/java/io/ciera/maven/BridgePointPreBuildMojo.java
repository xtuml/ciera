package io.ciera.maven;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

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
      final String workspace =
          null == this.workspace || "".equals(this.workspace)
              ? System.getenv("WORKSPACE")
              : this.workspace;
      final String bpHome =
          null == this.bpHome || "".equals(this.bpHome) ? System.getenv("BPHOME") : this.bpHome;
      final String cliExe = bpHome + File.separator + BIN_DIR + File.separator + "CLI.sh";
      if (workspace == null
          || "".equals(workspace.trim())
          || bpHome == null
          || "".equals(bpHome.trim())) {
        if (bpHome == null || "".equals(bpHome.trim())) {
          getLog().error("BPHOME is unset.");
        }
        if (workspace == null || "".equals(workspace.trim())) {
          getLog().error("WORKSPACE is unset.");
        }
        throw new MojoFailureException("Could not execute pre-build.");
      } else {
        ProcessBuilder pb =
            new ProcessBuilder(cliExe, "Build", "-project", projectName, "-prebuildOnly")
                .redirectOutput(Redirect.PIPE)
                .redirectError(Redirect.PIPE);
        pb.environment().put("WORKSPACE", workspace);
        getLog()
            .info("Performing BridgePoint pre-build (workspace location: " + workspace + ")...");
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
          Files.copy(
              Path.of(
                  project.getBasedir().getPath(), "gen", "code_generation", projectName + ".sql"),
              Path.of(outputFile));
          int duration = (int) (System.currentTimeMillis() - startTime);
          int mins = duration / 60000;
          int secs = (duration % 60000) / 1000;
          int msecs = duration % 1000;
          getLog().info("");
          getLog().info(String.format("Pre-build duration: %d:%d.%03d", mins, secs, msecs));
        } catch (IOException e) {
          getLog().error("Problem executing pre-builder:", e);
        }
      }
    } else {
      getLog().info("Pre-build output up to date.");
    }
  }
}
