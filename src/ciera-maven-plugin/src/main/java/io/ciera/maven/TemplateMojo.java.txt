package io.ciera.maven;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import io.ciera.runtime.summit.application.IApplication;
import io.ciera.tool.TemplateTool;

/**
 * Goal which runs the Template builder.
 */
@Mojo(name = "template", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class TemplateMojo extends AbstractCieraMojo {

    @Parameter(defaultValue = "${localRepository}", readonly = true, required = true)
    private ArtifactRepository localRepository;

    @Parameter
    private String[] templateDirs;

    @Parameter(defaultValue = "true")
    private boolean includeDependencyTemplates;

    protected IApplication getTool() {
        return new TemplateTool();
    }

    protected List<String> getAdditionalArguments() {
        List<String> args = new ArrayList<>();
        args.add("--template-path");
        String templatePath = "";
        String sep = "";
        if (templateDirs != null) {
            for (String templateDir : templateDirs) {
                templatePath += sep + templateDir;
                sep = ":";
            }
        }
        for (String dependencyPath : getDependencyPaths()) {
            templatePath += sep + dependencyPath;
            sep = ":";

        }
        args.add(templatePath);
        return args;
    }

    private List<String> getDependencyPaths() {
        List<String> dependencyPaths = new ArrayList<>();
        for (Artifact artifact : project.getDependencyArtifacts()) {
            Path artifactPath = Paths.get(localRepository.getBasedir(), localRepository.pathOf(artifact));
            dependencyPaths.add(artifactPath.toString());
        }
        return dependencyPaths;
    }

}
