package io.ciera.maven;

import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;

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

  @Parameter(defaultValue = "${localRepository}", readonly = true, required = true)
  protected ArtifactRepository localRepository;

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

  protected List<Artifact> getDependencyModels() {
    List<Artifact> dependencyModels = new ArrayList<>();
    for (Artifact artifact : project.getDependencyArtifacts()) {
      Path artifactPath = Paths.get(localRepository.getBasedir(), localRepository.pathOf(artifact));
      ZipFile zipfile = null;
      try {
        zipfile = new ZipFile(artifactPath.toFile());
      } catch (IOException e) {
        /* do nothing */
      }
      if (null != zipfile) {
        Enumeration<? extends ZipEntry> entries = zipfile.entries();
        while (entries.hasMoreElements()) {
          ZipEntry entry = entries.nextElement();
          if (entry.getName().endsWith(".xtuml")) {
            dependencyModels.add(artifact);
            break;
          }
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
