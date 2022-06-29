package io.ciera.runtime.api.types;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class ReadWriteDevice extends ReadableDevice {

  private final PrintStream out;

  public ReadWriteDevice(final String name, final InputStream in, final OutputStream out) {
    super(name, in);
    this.out = new PrintStream(out);
  }

  @Override
  public void write(final Object o) {
    out.print(o);
  }

  @Override
  public void writeLine(final Object o) {
    write(o);
    out.println();
  }

  @Override
  public void flush() {
    out.flush();
  }

  @Override
  public void close() {
    super.close();
    out.close();
  }
}
