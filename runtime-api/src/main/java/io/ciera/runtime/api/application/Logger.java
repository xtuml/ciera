package io.ciera.runtime.api.application;

/**
 * This interface is based on the Logger interface from the SLF4J project. The goal is to create an
 * interface without external dependencies but can be used with a SLF4J compliant logger.
 *
 * @see org.slf4j.Logger
 */
public interface Logger {

  String ANSI_RESET = "\u001B[0m";
  String ANSI_BLACK = "\u001B[30m";
  String ANSI_RED = "\u001B[31m";
  String ANSI_GREEN = "\u001B[32m";
  String ANSI_YELLOW = "\u001B[33m";
  String ANSI_BLUE = "\u001B[34m";
  String ANSI_PURPLE = "\u001B[35m";
  String ANSI_CYAN = "\u001B[36m";
  String ANSI_WHITE = "\u001B[37m";

  String getName();

  boolean isTraceEnabled();

  void trace(String msg);

  void trace(String format, Object arg);

  void trace(String format, Object arg1, Object arg2);

  void trace(String format, Object... arguments);

  void trace(String msg, Throwable t);

  boolean isDebugEnabled();

  void debug(String msg);

  void debug(String format, Object arg);

  void debug(String format, Object arg1, Object arg2);

  void debug(String format, Object... arguments);

  void debug(String msg, Throwable t);

  boolean isInfoEnabled();

  void info(String msg);

  void info(String format, Object arg);

  void info(String format, Object arg1, Object arg2);

  void info(String format, Object... arguments);

  void info(String msg, Throwable t);

  boolean isWarnEnabled();

  void warn(String msg);

  void warn(String format, Object arg);

  void warn(String format, Object... arguments);

  void warn(String format, Object arg1, Object arg2);

  void warn(String msg, Throwable t);

  boolean isErrorEnabled();

  void error(String msg);

  void error(String format, Object arg);

  void error(String format, Object arg1, Object arg2);

  void error(String format, Object... arguments);

  void error(String msg, Throwable t);
}
