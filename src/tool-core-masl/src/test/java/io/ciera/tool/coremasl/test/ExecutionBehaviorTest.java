package io.ciera.tool.coremasl.test;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import io.ciera.runtime.instanceloading.generic.IGenericLoader;
import io.ciera.tool.CoreMaslTool;

public class ExecutionBehaviorTest {

    private static final String RESOURCE_PREFIX = "/io/ciera/tool/coremasl/test";
    private static final String CIERA_VERSION = System.getProperty("projectVersion");
    private static final String MVN_REPO = System.getProperty("localRepository");
    private static final String CLASSPATH = MVN_REPO + "/io/ciera/runtime-api/" + CIERA_VERSION + "/runtime-api-"
            + CIERA_VERSION + ".jar:" + MVN_REPO + "/io/ciera/runtime/" + CIERA_VERSION + "/runtime-" + CIERA_VERSION
            + ".jar";

    private IGenericLoader loader;
    private Path srcDir;
    private Path binDir;

    private void createDirectories() throws IOException {
        srcDir = Files.createDirectories(Path.of(System.getProperty("baseDirectory"))
                .relativize(Path.of(System.getProperty("testDirectory"), "src")));
        binDir = Files.createDirectories(Path.of(System.getProperty("baseDirectory"))
                .relativize(Path.of(System.getProperty("testDirectory"), "bin")));
    }

    private void setupLoader() {
        loader = new TestImportParser(RESOURCE_PREFIX + "/model/HelloWorld.mod", RESOURCE_PREFIX + "/model/hello.svc");
    }

    private void generateCode() {
        CoreMaslTool compiler = new CoreMaslTool();
        compiler.setup(new String[] { "--gendir", srcDir.toString() });
        compiler.initialize();
        compiler.CoreMasl().LOAD().setDefaultLoader(loader);
        compiler.start();
    }

    private void compileJava() throws IOException, InterruptedException {
        System.out.println("compiling...");
        new ProcessBuilder(
                List.of("javac", "-cp", CLASSPATH, "-d", binDir.toString(), srcDir.toString() + "/**/*.java")).start()
                        .waitFor();
    }

    private void runJava() throws InterruptedException, IOException {
        System.out.println("running...");
        ProcessBuilder pb = new ProcessBuilder(List.of("java", "-cp", CLASSPATH + ":" + binDir.toString(),
                "helloworldapplication.HelloWorldApplication")).redirectError(Redirect.PIPE)
                        .redirectOutput(Redirect.PIPE);
        Process proc = pb.start();
        Scanner sc = new Scanner(proc.getInputStream());
        while (sc.hasNextLine()) {
            System.out.println("OUTPUT: " + sc.nextLine());
        }
        sc.close();
        proc.waitFor(1, TimeUnit.SECONDS);
    }

    @Test
    public void test() throws IOException, InterruptedException {
        createDirectories();
        setupLoader();
        generateCode();
        compileJava();
        runJava();
    }

}
