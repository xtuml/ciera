package io.ciera.maven;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;

import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.exceptions.XtumlExitException;

public abstract class AbstractXtumlCieraMojo extends AbstractCieraMojo {

  @Parameter(defaultValue = "${project.build.directory}/${project.name}.sql")
  protected String input;

  @Parameter(defaultValue = "")
  protected String output;

  @Override
  public void execute() throws MojoExecutionException {
    initProperties();
    String inFile =
        null == input
            ? ""
            : project.getBasedir().toURI().relativize(new File(input).toURI()).getPath();
    String outFile =
        null == output
            ? ""
            : project.getBasedir().toURI().relativize(new File(output).toURI()).getPath();
    String genDirPath = project.getBasedir().toURI().relativize(new File(genDir).toURI()).getPath();
    IApplication compiler = getTool();
    List<String> args = new ArrayList<>();
    args.add("-i");
    args.add(inFile);
    args.add("-o");
    args.add(outFile);
    args.add("-p");
    args.add(project.getName());
    args.add("--cwd");
    args.add(project.getBasedir().getPath());
    args.add("--gendir");
    args.add(genDirPath);
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
}
