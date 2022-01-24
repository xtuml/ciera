package io.ciera.tool.coremasl.test.util;

import java.io.PrintStream;
import java.util.List;

import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.types.Device;
import io.ciera.runtime.api.types.ReadableDevice;

public class TRACE {

	private static Device debug = new TraceConsole("DEBUG");
	private static Device info = new TraceConsole("INFO");
	private static Device warn = new TraceConsole("WARN");
	private static Device error = new TraceConsole("ERROR");

	public TRACE(Domain domain) {
	}

	public Device debug() {
		return debug;
	}

	public Device info() {
		return info;
	}

	public Device warn() {
		return warn;
	}

	public Device error() {
		return error;
	}

	private static final class TraceConsole extends ReadableDevice {

		private final String flavor;
		private final List<PrintStream> outputs;
		private StringBuilder outputBuffer;

		private TraceConsole(String flavor) {
			super(flavor + "TraceConsole", System.in);
			this.outputs = List.of(new PrintStream(System.out));
			this.flavor = flavor;
			this.outputBuffer = new StringBuilder();
		}

		@Override
		public void write(Object o) {
			o.toString().chars().forEach(i -> {
				if (i == '\n') {
					outputs.stream().forEach(out -> out.println(flavor + ": " + outputBuffer));
					outputBuffer = new StringBuilder();
				} else {
					outputBuffer.append((char) i);
				}
			});
		}

		@Override
		public void writeLine(Object o) {
			write(o);
			write('\n');
		}
		
	}

}