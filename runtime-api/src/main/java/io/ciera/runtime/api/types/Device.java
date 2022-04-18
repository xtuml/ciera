package io.ciera.runtime.api.types;

import java.io.Closeable;
import java.io.Flushable;

import io.ciera.runtime.api.exceptions.DeserializationException;

/**
 * A device represents a readable or read-writable object. Streams can be read from devices or
 * directed as output to devices.
 */
public abstract class Device implements Closeable, AutoCloseable, Flushable {

  public static final Device CONSOLE = new ReadWriteDevice("CONSOLE", System.in, System.out);
  public static final Device NULL = new DevNull();

  private String name;

  public Device(String name) {
    this.name = name;
  }

  /**
   * Read a single token from the device stream and attempt to convert the token to type {@code
   * cls}. The tokenization of the stream is left up to the individual implementation, however, by
   * default tokens are delimited by whitespace.
   *
   * @param cls The type to interpret tokens as.
   * @return A parsed instance of the type represented by the token.
   */
  public abstract <T extends Object> T read(Class<T> cls);

  /**
   * Read a single line from the stream and return as a String. The line terminator is determined by
   * the system.
   *
   * @return a line of text from the stream.
   */
  public abstract String readLine();

  /**
   * Convert an instance of an object to a String and append to the stream.
   *
   * @param o The instance to write.
   */
  public abstract void write(Object o);

  /**
   * Convert an instance of an object to a String and append to the stream. Append an additional
   * line separator. The line separator is determined by the system.
   *
   * @param o
   */
  public abstract void writeLine(Object o);

  /** Flush the output stream. */
  @Override
  public abstract void flush();

  @Override
  public String toString() {
    return name;
  }

  public static Device fromString(String s) {
    throw new DeserializationException("'Device' type is not serializable");
  }

  private static final class DevNull extends Device {

    private DevNull() {
      super("NULL");
    }

    @Override
    public <T> T read(Class<T> cls) {
      throw new UnsupportedOperationException("Cannot read from null device");
    }

    @Override
    public String readLine() {
      throw new UnsupportedOperationException("Cannot read from null device");
    }

    @Override
    public void write(Object o) {}

    @Override
    public void writeLine(Object o) {}

    @Override
    public void flush() {}

    @Override
    public void close() {}
  }
}
