package io.ciera.maven;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipFile;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.LocalArtifactRequest;
import org.eclipse.aether.repository.LocalArtifactResult;

/** Goal which runs the BridgePoint pre-builder. */
public abstract class AbstractPreBuildMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project.name}")
  protected String projectName;

  @Parameter(readonly = true, defaultValue = "${project}")
  protected MavenProject project;

  @Parameter(defaultValue = "${project.build.directory}/${project.name}.sql")
  protected String outputFile;

  @Parameter(defaultValue = "true")
  protected boolean includeDependencyModels;

  @Parameter(defaultValue = "${repositorySystemSession}", readonly = true, required = true)
  protected RepositorySystemSession repositorySystemSession;

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
        for (File child :
            Arrays.stream(testFile.list())
                .map((filename) -> new File(testFile, filename))
                .toArray(File[]::new)) {
          isNewer |= fileIsNewer(baseFile, child);
        }
      }
    }
    return isNewer;
  }

  protected List<LocalArtifactResult> getDependencyModels() {
    List<LocalArtifactResult> dependencyModels = new ArrayList<>();
    for (Dependency dependency : project.getDependencies()) {
      getLog()
          .debug(
              String.format(
                  "Searching for models in dependency %s:%s:%s",
                  dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion()));
      final LocalArtifactResult result =
          repositorySystemSession
              .getLocalRepositoryManager()
              .find(
                  repositorySystemSession,
                  new LocalArtifactRequest(
                      new DefaultArtifact(
                          dependency.getGroupId(),
                          dependency.getArtifactId(),
                          "jar",
                          dependency.getVersion()),
                      Collections.emptyList(),
                      null));
      ZipFile zipfile = null;
      if (result.getFile() != null) {
        try {
          zipfile = new ZipFile(result.getFile());
        } catch (IOException e) {
          getLog()
              .debug(
                  String.format(
                      "Could not create ZipFile for: %s:%s:%s",
                      dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion()),
                  e);
        }
      } else {
        getLog()
            .debug(
                String.format(
                    "Cannot find file for dependency %s:%s:%s",
                    dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion()));
      }
      if (zipfile != null) {
        if (Collections.list(zipfile.entries()).stream()
            .anyMatch(entry -> entry.getName().toLowerCase().endsWith(".xtuml"))) {
          dependencyModels.add(result);
        } else {
          getLog()
              .debug(
                  String.format(
                      "Dependency %s:%s:%s contains no .xtuml files",
                      dependency.getGroupId(),
                      dependency.getArtifactId(),
                      dependency.getVersion()));
        }
      }
    }
    return dependencyModels;
  }

  protected void runProcess(final String command, final ProcessBuilder pb)
      throws MojoFailureException {
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
      if (proc.waitFor() == 0) {
        int duration = (int) (System.currentTimeMillis() - startTime);
        int mins = duration / 60000;
        int secs = (duration % 60000) / 1000;
        int msecs = duration % 1000;
        getLog().info(String.format("%s duration: %d:%d.%03d", command, mins, secs, msecs));
      } else {
        getLog().error(String.format("%s exited with code %d", command, proc.exitValue()));
        throw new MojoFailureException(
            String.format("%s exited with code %d", command, proc.exitValue()));
      }
    } catch (IOException | InterruptedException e) {
      getLog().error("Problem executing " + command + ":", e);
      throw new MojoFailureException("Problem executing " + command + ":", e);
    }
  }
}
