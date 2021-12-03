package io.ciera.maven;

import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.exceptions.XtumlExitException;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import org.sonatype.plexus.build.incremental.BuildContext;

public abstract class AbstractCieraMojo extends AbstractMojo {

    /** @component */
    private BuildContext buildContext;

    @Parameter(defaultValue = "${project.build.directory}/${project.name}.sql")
    protected String input;

    @Parameter(defaultValue = "")
    protected String output;

    @Parameter(defaultValue = "${project.build.directory}/generated-sources/java")
    protected String genDir;

    @Parameter(readonly = true, defaultValue = "${project}")
    protected MavenProject project;

    @Override
    public void execute() throws MojoExecutionException {
        String inFile = null == input ? "" : project.getBasedir().toURI().relativize(new File(input).toURI()).getPath();
        String outFile = null == output ? ""
                : project.getBasedir().toURI().relativize(new File(output).toURI()).getPath();
        String genDirPath = project.getBasedir().toURI().relativize(new File(genDir).toURI()).getPath();
        IApplication compiler = getTool();
        List<String> args = new ArrayList<>();
        args.add("-i");
        args.add(inFile);
        args.add("-o");
        args.add(outFile);
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

    protected void postInitialize(IApplication compiler) {
        // no-op
    }

    private void refreshFiles() {
        if (null != buildContext) {
            buildContext.refresh(new File(genDir));
        }
    }

    private void addSrcGen() {
        File srcGen = new File(genDir);
        if (srcGen.exists()) {
            project.addCompileSourceRoot(srcGen.getAbsolutePath());
        }
    }

    private void copyCustomCode() {
        copyCustomCode(new File(project.getBuild().getSourceDirectory()), "");
    }

    private void copyCustomCode(File dir, String path) {
        if (null != dir && dir.exists()) {
            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    copyCustomCode(file, "".equals(path) ? file.getName() : path + File.separator + file.getName());
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
