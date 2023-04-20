package io.ciera.maven;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.LocalArtifactRequest;
import org.eclipse.aether.repository.LocalArtifactResult;
import org.xtuml.stratus.parser.MaslImportParser;

import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.exceptions.XtumlExitException;
import io.ciera.tool.CoreMaslTool;

/** Goal which runs the Core builder for MASL models. */
@Mojo(name = "core-masl", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class CoreMaslMojo extends AbstractCieraMojo {

  @Parameter(defaultValue = "${repositorySystemSession}", readonly = true, required = true)
  protected RepositorySystemSession repositorySystemSession;

  @Parameter private String modFile;

  @Parameter private String prjFile;

  @Parameter private String[] domainPaths;

  @Override
  protected IApplication getTool() {
    return new CoreMaslTool();
  }

  @Override
  protected void postInitialize(IApplication compiler) {
    ((CoreMaslTool) compiler).CoreMasl().LOAD().setDefaultLoader(new MaslImportParser());
  }

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    initProperties();
    IApplication compiler = getTool();
    List<String> args = new ArrayList<>();
    if (prjFile != null) {
      args.add("--prj");
      args.add(prjFile);
    } else if (modFile != null) {
      args.add("--mod");
      args.add(modFile);
    } else {
      try {
        args.addAll(findModelPath());
      } catch (UncheckedIOException | IllegalStateException e) {
        throw new MojoFailureException("Could not find model file to translate.", e);
      }
    }
    List<String> domainPaths =
        new ArrayList<>(List.of(this.domainPaths == null ? new String[0] : this.domainPaths));
    domainPaths.addAll(
        getInterfaceJars().stream()
            .map(artifact -> artifact.getFile().toString())
            .collect(Collectors.toList()));
    if (!domainPaths.isEmpty()) {
      args.add("--domainpath");
      args.add(domainPaths.stream().collect(Collectors.joining(":")));
    }
    getInterfaceJars();
    args.add("--output");
    args.add(genDir);
    args.addAll(getAdditionalArguments());
    try {
      compiler.setup(args.toArray(new String[0]), new CieraMavenLogger(getLog()));
      compiler.initialize();
      this.postInitialize(compiler);
      compiler.start();
    } catch (XtumlExitException e) {
      throw new MojoExecutionException("Ciera compiler failure", e);
    }
    copyCustomCode();
    addSrcGen();
    refreshFiles();
  }

  /**
   * Look in a well known location for exactly one .mod or .prj file
   *
   * @return the --mod or --prj argument and the path to the file
   * @throws MojoFailureException if exactly one model file is not found
   */
  private List<String> findModelPath() {
    return Stream.of(
            Path.of(project.getBasedir().toString(), "src", "main", "masl"),
            Path.of(project.getBuild().getDirectory(), "generated-sources", "masl"))
        .filter(p -> p.toFile().exists())
        .flatMap(
            p -> {
              try {
                return Files.walk(p);
              } catch (IOException e) {
                throw new UncheckedIOException(e);
              }
            })
        .filter(p -> p.toString().endsWith(".mod") || p.toString().endsWith(".prj"))
        .collect(
            Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                  if (list.size() != 1) {
                    throw new IllegalStateException();
                  }
                  final String modelPath = list.get(0).toString();
                  return List.of(modelPath.endsWith(".mod") ? "--mod" : "--prj", modelPath);
                }));
  }

  private List<LocalArtifactResult> getInterfaceJars() {
    List<LocalArtifactResult> dependencyModels = new ArrayList<>();
    for (Dependency dependency : project.getDependencies()) {
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
      if (result.isAvailable()) {
        try {
          zipfile = new ZipFile(result.getFile());
        } catch (IOException e) {
          /* do nothing */
        }
      }
      if (zipfile != null) {
        Enumeration<? extends ZipEntry> entries = zipfile.entries();
        while (entries.hasMoreElements()) {
          ZipEntry entry = entries.nextElement();
          if (entry.getName().endsWith(".int")) {
            dependencyModels.add(result);
            break;
          }
        }
      }
    }
    return dependencyModels;
  }
}
