package io.ciera.tool.coremasl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import io.ciera.runtime.instanceloading.generic.IGenericLoader;
import io.ciera.tool.CoreMaslTool;

public class ExecutionBehaviorTest {

	private static final String APP_NAME = "CoreMaslTest";
	private static final String APP_PACKAGE = ExecutionBehaviorTest.class.getPackageName();

	private static final String RESOURCE_PREFIX = "masl/tests/";

	private static final String CIERA_VERSION = System.getProperty("projectVersion");
	private static final String MVN_REPO = System.getProperty("localRepository");
	private static final String RUNTIME_API_PATH = String.format("%s/io/ciera/runtime-api/%s/runtime-api-%s.jar", MVN_REPO, CIERA_VERSION, CIERA_VERSION);
	private static final String RUNTIME_PATH = String.format("%s/io/ciera/runtime/%s/runtime-%s.jar", MVN_REPO, CIERA_VERSION, CIERA_VERSION);
	private static final String RUNTIME_UTIL_PATH = String.format("%s/io/ciera/runtime-util/%s/runtime-util-%s.jar", MVN_REPO, CIERA_VERSION, CIERA_VERSION);
	private static final String CLASSPATH = String.join(":", RUNTIME_API_PATH, RUNTIME_PATH, RUNTIME_UTIL_PATH);

	private Path srcDir;
	private Path binDir;

	private void initDirectories(String testCase) throws IOException {
		final Path baseDir = Path.of(System.getProperty("user.dir"));
		srcDir = baseDir.relativize(Path.of(System.getProperty("testDirectory"), testCase, "src"));
		binDir = baseDir.relativize(Path.of(System.getProperty("testDirectory"), testCase, "bin"));
	}

	private Stream<String> getModelResources(String testCase) throws IOException {
		Stream<String> intFiles = getJarResources(RESOURCE_PREFIX + testCase).filter(s -> s.endsWith(".int"));
		Stream<String> modFiles = getJarResources(RESOURCE_PREFIX + testCase).filter(s -> s.endsWith(".mod"));
		Stream<String> activityFiles = getJarResources(RESOURCE_PREFIX + testCase)
				.filter(s -> !s.endsWith(".int") && !s.endsWith(".mod"));
		return Stream.concat(Stream.concat(intFiles, modFiles), activityFiles)
				.map(r -> "/" + RESOURCE_PREFIX + testCase + "/" + r);
	}

	private void generateCode(String testCase) throws IOException {
		System.out.println("Generating code...");

		// set up system marks
		System.setProperty("io.ciera.ApplicationName", APP_NAME);
		System.setProperty("io.ciera.ApplicationPackage", APP_PACKAGE);
		System.setProperty("io.ciera.IdleHalt", "true");

		// pass resources to MASL loader
		IGenericLoader loader = new TestImportParser(getModelResources(testCase));

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

	private Stream<String> getJarResources(String rootPath, boolean recursive, JarFile jarFile) {
		return jarFile.stream().map(JarEntry::getName).filter(s -> !s.equals(rootPath) && s.startsWith(rootPath))
				.filter(s -> recursive || rootPath.split("/").length == s.split("/").length - 1)
				.map(s -> s.substring(rootPath.length()).replaceAll("/", ""));
	}

	private Stream<String> getJarResources(String rootPath, boolean recursive) throws IOException {
		URL url = getClass().getResource("/" + rootPath);
		if (url == null)
			throw new IllegalArgumentException("Could not find resource: /" + rootPath);
		String scheme = url.getProtocol();
		if (!"jar".equals(scheme))
			throw new IllegalArgumentException("Unsupported scheme: " + scheme);
		JarURLConnection con = (JarURLConnection) url.openConnection();
		JarFile jarFile = con.getJarFile();
		return getJarResources(rootPath, recursive, jarFile);
	}

	private Stream<String> getJarResources(String rootPath) throws IOException {
		return getJarResources(rootPath, false);
	}

	@TestFactory
	public Stream<DynamicTest> runAllTests() throws IOException {
		return getJarResources(RESOURCE_PREFIX).sorted().map(testCase -> DynamicTest.dynamicTest(testCase, () -> {
			System.out.println("Runnint test: " + testCase);
			initDirectories(testCase);
			generateCode(testCase);
			compileJava();
			runJava();
			System.out.println("Done.\n");
		}));
	}

}