package io.ciera.tool.coremasl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private Path srcDir;
    private Path binDir;

    private void initDirectories() throws IOException {
        final Path baseDir = Path.of(System.getProperty("user.dir"));
        srcDir = baseDir.relativize(Path.of(System.getProperty("testDirectory"), "src"));
        binDir = baseDir.relativize(Path.of(System.getProperty("testDirectory"), "bin"));
    }

    private void generateCode() {
        System.out.println("Generating code...");
        IGenericLoader loader = new TestImportParser(RESOURCE_PREFIX + "/model/HelloWorld.mod",
                RESOURCE_PREFIX + "/model/hello.svc");
        CoreMaslTool compiler = new CoreMaslTool();
        compiler.setup(new String[] { "--gendir", srcDir.toString() });
        compiler.initialize();
        compiler.CoreMasl().LOAD().setDefaultLoader(loader);
        compiler.start();
    }

    private void compileJava() throws IOException, InterruptedException {
        System.out.println("Compiling...");
        Stream<String> srcFileList = Files.walk(srcDir).map(Path::toString).filter(f -> f.endsWith(".java"));
        List<String> cmd = Stream.concat(Stream.of("javac", "-cp", CLASSPATH, "-d", binDir.toString()), srcFileList)
                .collect(Collectors.toList());
        System.out.println(String.join(" ", cmd));
        Process proc = new ProcessBuilder(cmd).start();
        String errOutput = new BufferedReader(new InputStreamReader(proc.getErrorStream())).lines()
                .collect(Collectors.joining("\n"));
        int code = proc.waitFor();
        assertEquals(0, code, "Failed to compile generated Java:\n\n" + errOutput);
    }

    private void runJava() throws InterruptedException, IOException {
        System.out.println("Running...");
        List<String> cmd = List.of("java", "-cp", String.join(":", CLASSPATH, binDir.toString()),
                "helloworldapplication.HelloWorldApplication");
        System.out.println(String.join(" ", cmd));
        Process proc = new ProcessBuilder(cmd).start();
        new Thread(() -> new BufferedReader(new InputStreamReader(proc.getInputStream())).lines()
                .forEach(System.out::println)).start();
        final StringBuilder errOutput = new StringBuilder();
        new Thread(() -> new BufferedReader(new InputStreamReader(proc.getErrorStream())).lines()
                .forEach(l -> errOutput.append(l).append("\n"))).start();
        if (!proc.waitFor(1, TimeUnit.SECONDS)) {
            System.out.println("Killing process...");
            proc.destroy();
        } else {
            int code = proc.exitValue();
            assertEquals(0, code, "Test case execution resulted in errors:\n\n" + errOutput);
        }
    }

    @Test
    public void test() throws IOException, InterruptedException {
        initDirectories();
        generateCode();
        compileJava();
        runJava();
    }

}
