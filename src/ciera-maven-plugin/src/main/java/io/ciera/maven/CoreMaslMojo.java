package io.ciera.maven;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.DefaultDependencyResolutionRequest;
import org.apache.maven.project.DefaultProjectDependenciesResolver;
import org.apache.maven.project.DependencyResolutionException;
import org.xtuml.stratus.parser.MaslImportParser;

import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.exceptions.XtumlExitException;
import io.ciera.tool.CoreMaslTool;

/**
 * Goal which runs the Core builder for MASL models.
 */
@Mojo(name = "core-masl", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class CoreMaslMojo extends AbstractCieraMojo {

    @Parameter(defaultValue = "${localRepository}", readonly = true, required = true)
    private ArtifactRepository localRepository;

    @Parameter
    private String modFile;

    @Parameter
    private String prjFile;

    @Parameter
    private String[] domainPaths;

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
            } catch (IOException | IllegalStateException e) {
                throw new MojoFailureException("Could not find model file to translate.", e);
            }
        }
        List<String> domainPaths = new ArrayList<>(List.of(this.domainPaths == null ? new String[0] : this.domainPaths));
        domainPaths.addAll(getInterfaceJars());
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
    private List<String> findModelPath() throws IOException {
        return Files.walk(Path.of(project.getBasedir().toString(), "src", "main", "masl"))
                .filter(p -> p.toString().endsWith(".mod") || p.toString().endsWith(".prj"))
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    final String modelPath = list.get(0).toString();
                    return List.of(modelPath.endsWith(".mod") ? "--mod" : "--prj", modelPath);
                }));
    }

    private Set<String> getInterfaceJars() {
        return project.getDependencyArtifacts().stream()
                .map(dependency -> Paths.get(localRepository.getBasedir(), localRepository.pathOf(dependency)))
                .filter(artifactPath -> {
                    try (ZipFile zipfile = new ZipFile(artifactPath.toFile())) {
                        return zipfile.stream().map(ZipEntry::getName)
                                .anyMatch(name -> name.startsWith("masl") && name.endsWith(".int"));
                    } catch (IOException e) {
                        return false;
                    }
                }).map(Path::toString).collect(Collectors.toSet());
    }

}
