package io.ciera.tool.coremasl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import io.ciera.runtime.instanceloading.generic.IGenericLoader;
import io.ciera.tool.CoreMaslTool;

public class ExecutionBehaviorTest {

	private static final String APP_NAME = "CoreMaslTest";
	private static final String APP_PACKAGE = ExecutionBehaviorTest.class.getPackageName();

	private static final String RESOURCE_PREFIX = "/masl/tests";
	private static final String CIERA_VERSION = System.getProperty("projectVersion");
	private static final String MVN_REPO = System.getProperty("localRepository");
	private static final String RUNTIME_API_PATH = MVN_REPO + "/io/ciera/runtime-api/" + CIERA_VERSION + "/runtime-api-"
			+ CIERA_VERSION + ".jar";
	private static final String RUNTIME_PATH = MVN_REPO + "/io/ciera/runtime/" + CIERA_VERSION + "/runtime-"
			+ CIERA_VERSION + ".jar";
	private static final String RUNTIME_UTIL_PATH = MVN_REPO + "/io/ciera/runtime-util/" + CIERA_VERSION
			+ "/runtime-util-" + CIERA_VERSION + ".jar";
	private static final String CLASSPATH = String.join(":", RUNTIME_API_PATH, RUNTIME_PATH, RUNTIME_UTIL_PATH);

	private Path srcDir;
	private Path binDir;

	private void initDirectories(String testCase) throws IOException {
		final Path baseDir = Path.of(System.getProperty("user.dir"));
		srcDir = baseDir.relativize(Path.of(System.getProperty("testDirectory"), testCase, "src"));
		binDir = baseDir.relativize(Path.of(System.getProperty("testDirectory"), testCase, "bin"));
	}

	private void generateCode(String testCase) {
		System.out.println("Generating code...");

		// set up system marks
		System.setProperty("io.ciera.ApplicationName", APP_NAME);
		System.setProperty("io.ciera.ApplicationPackage", APP_PACKAGE);
		System.setProperty("io.ciera.IdleHalt", "true");

		// pass resources to MASL loader
		IGenericLoader loader = new TestImportParser(RESOURCE_PREFIX + "/" + testCase + "/A.int",
				RESOURCE_PREFIX + "/" + testCase + "/Model1.mod", RESOURCE_PREFIX + "/" + testCase + "/init.svc");

		// create compiler and run generation
		CoreMaslTool compiler = new CoreMaslTool();
		compiler.setup(new String[] { "--gendir", srcDir.toString() });
		compiler.initialize();
		compiler.CoreMasl().LOAD().setDefaultLoader(loader);
		compiler.start();

		// reset system marks
		System.clearProperty("io.ciera.ApplicationName");
		System.clearProperty("io.ciera.ApplicationPackage");
		System.clearProperty("io.ciera.IdleHalt");
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
				APP_PACKAGE + "." + APP_NAME);
		System.out.println(String.join(" ", cmd));
		Process proc = new ProcessBuilder(cmd).start();
		new BufferedReader(new InputStreamReader(proc.getInputStream())).lines().forEach(System.out::println);
		String errOutput = new BufferedReader(new InputStreamReader(proc.getErrorStream())).lines()
				.collect(Collectors.joining("\n"));
		int code = proc.exitValue();
		assertEquals(0, code, "Test case execution resulted in errors:\n\n" + errOutput);
	}

	@TestFactory
	public Stream<DynamicTest> runAllTests() {
		return Stream.of("test1", "test2").map(testCase -> DynamicTest.dynamicTest(testCase, () -> {
			initDirectories(testCase);
			generateCode(testCase);
			compileJava();
			runJava();
		}));
	}

}
