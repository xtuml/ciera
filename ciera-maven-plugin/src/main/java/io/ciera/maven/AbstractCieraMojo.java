package io.ciera.maven;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.sonatype.plexus.build.incremental.BuildContext;

import io.ciera.runtime.summit.application.IApplication;

public abstract class AbstractCieraMojo extends AbstractMojo {

  /** @component */
  private BuildContext buildContext;

  @Parameter(defaultValue = "${project.build.directory}/generated-sources/java")
  protected String genDir;

  @Parameter(readonly = true, defaultValue = "${project}")
  protected MavenProject project;

  @Parameter private Map<String, String> systemMarks;

  protected void postInitialize(IApplication compiler) {
    // no-op
  }

  protected void initProperties() {
    if (systemMarks != null) {
      for (String prop : systemMarks.keySet()) {
        System.setProperty("io.ciera." + prop, systemMarks.get(prop));
      }
    }
  }

  protected void refreshFiles() {
    if (null != buildContext) {
      buildContext.refresh(new File(genDir));
    }
  }

  protected void addSrcGen() {
    File srcGen = new File(genDir);
    if (srcGen.exists()) {
      project.addCompileSourceRoot(srcGen.getAbsolutePath());
    }
  }

  protected void copyCustomCode() {
    copyCustomCode(new File(project.getBuild().getSourceDirectory()), "");
  }

  private void copyCustomCode(File dir, String path) {
    if (null != dir && dir.exists()) {
      if (dir.isDirectory()) {
        for (File file : dir.listFiles()) {
          copyCustomCode(
              file, "".equals(path) ? file.getName() : path + File.separator + file.getName());
        }
      } else {
        File genFile = Paths.get(genDir, path).toFile();
        File genFileOrig = Paths.get(genDir, path + ".orig").toFile();
        if (genFile.exists() && !genFile.isDirectory()) {
          genFile.renameTo(genFileOrig);
        }
      }
    }
  }

  protected List<String> getAdditionalArguments() {
    return new ArrayList<>();
  }

  protected abstract IApplication getTool();
}
