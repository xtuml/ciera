package io.ciera.maven;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "xtuml2masl", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class Xtuml2MaslMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project.basedir}/")
  private String input;

  @Parameter(defaultValue = "${project.build.directory}/generated-sources/masl")
  private String output;

  @Parameter(defaultValue = "${project.name}-masl")
  private String resourceTargetPath;

  @Parameter(defaultValue = "false")
  private boolean includeFullModel;

  @Parameter private List<String> domains;

  @Parameter private List<String> deployments;

  @Parameter private File xtuml2masl;

  @Parameter(readonly = true, defaultValue = "${project}")
  private MavenProject project;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    // get the script location
    xtuml2masl =
        Optional.ofNullable(xtuml2masl)
            .orElseGet(
                () ->
                    Path.of(
                            Optional.ofNullable(
                                    project.getProperties().getProperty("bridgepoint.home"))
                                .or(() -> Optional.ofNullable(System.getenv("BPHOME")))
                                .orElse(""),
                            "tools",
                            "mc",
                            "bin",
                            "xtuml2masl")
                        .toFile());
    if (!xtuml2masl.canExecute()) {
      throw new MojoFailureException(
          "Cannot find xtuml2masl utility at location: " + xtuml2masl.toString());
    }

    // create the shell process for each export
    for (String domain : Optional.ofNullable(domains).orElse(List.of())) {
      runExport(List.of("-d", domain));
    }
    for (String deployment : Optional.ofNullable(deployments).orElse(List.of())) {
      runExport(List.of("-p", deployment));
    }

    // add generated MASL to the project resources
    addResources();
  }

  private void runExport(List<String> modelArgs) throws MojoFailureException {
    // create the shell process for each export
    List<String> cmd = new ArrayList<>();
    cmd.add(xtuml2masl.toString());
    cmd.addAll(List.of("-a", "MASL"));
    cmd.addAll(List.of("-i", input));
    cmd.addAll(List.of("-o", output));
    cmd.addAll(modelArgs);
    ProcessBuilder pb =
        new ProcessBuilder(cmd).redirectOutput(Redirect.PIPE).redirectError(Redirect.PIPE);
    getLog().info("Performing MASL export...");
    try {
      Instant t0 = Instant.now();
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
      if (proc.waitFor() == 0) {
        getLog().info(String.format("Export duration: %s", Duration.between(t0, Instant.now())));
      } else {
        getLog().error(String.format("pyxtuml exited with code %d", proc.exitValue()));
        throw new MojoFailureException(
            String.format("pyxtuml exited with code %d", proc.exitValue()));
      }
    } catch (IOException | InterruptedException e) {
      getLog().error("Problem executing MASL exporter:", e);
      throw new MojoFailureException("Problem executing MASL exporter", e);
    }
  }

  private void addResources() {
    final Resource resource = new Resource();
    resource.setTargetPath(resourceTargetPath);
    resource.setDirectory(output);
    resource.setFiltering(true);
    resource.setExcludes(List.of("export.log"));
    if (!includeFullModel) {
      resource.setIncludes(List.of("**/*.int"));
    }
    project.addResource(resource);
  }
}
