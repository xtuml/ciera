package io.ciera.maven;

import org.apache.maven.plugin.AbstractMojo;

import org.apache.maven.project.MavenProject;

import java.io.File;

import org.sonatype.plexus.build.incremental.BuildContext;

public abstract class AbstractCieraMojo extends AbstractMojo {

    /** @component */
    private BuildContext buildContext;

    public void refreshFiles() {
        if (null != buildContext) {
            File srcGen = new File(getProject().getBasedir(), "src-gen");
            buildContext.refresh(srcGen);
        }
    }

    public void addSrcGen() {
        File srcGen = new File(getProject().getBasedir(), "src-gen");
        if (srcGen.exists()) {
            getProject().addCompileSourceRoot(srcGen.getAbsolutePath());
        }
    }

    public void copyCustomCode() {
        copyCustomCode(new File(getProject().getBasedir(), "src"), "");
    }

    private void copyCustomCode(File dir, String path) {
        if (null != dir && dir.exists()) {
            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    copyCustomCode(file, "".equals(path) ? file.getName() : path + File.separator + file.getName());
                }
            }
            else {
                File genFile = new File(getProject().getBasedir(), "src-gen" + File.separator + path);
                File genFileOrig = new File(getProject().getBasedir(), "src-gen" + File.separator + path + ".orig");
                if (genFile.exists() && !genFile.isDirectory()) {
                    genFile.renameTo(genFileOrig);
                }
            }
        }
    }

    public abstract MavenProject getProject();

}
