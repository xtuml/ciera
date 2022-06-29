package io.ciera.runtime.application;

import java.io.OutputStream;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.application.Logger;

/**
 * This default logger implements the generic interface and is based internally on the JUL logging
 * system. It's default log level is INFO so debug and trace logs will be ignored by default.
 *
 * @see java.util.logging.Logger
 */
public class DefaultLogger implements Logger {

  private static final String LOG_LEVEL_PROP = "io.ciera.runtime.logLevel";

  private final java.util.logging.Logger internalLogger;

  public DefaultLogger(final String name) {
    this(name, null, Level.INFO, System.out);
  }

  public DefaultLogger(final String name, final Application application) {
    this(name, application, Level.INFO, System.out);
  }

  public DefaultLogger(
      final String name, final Application application, Level level, final OutputStream out) {
    // attempt to get the log level from a system property
    final String systemLogLevel = System.getProperty(LOG_LEVEL_PROP);
    if (systemLogLevel != null) {
      try {
        level = Level.parse(systemLogLevel);
      } catch (final IllegalArgumentException e) {
        /* fall back on passed in level */ }
    }
    internalLogger = java.util.logging.Logger.getLogger(name);
    internalLogger.setUseParentHandlers(false);
    internalLogger.setLevel(level);
    final ConsoleHandler handler = new ConsoleHandler();
    handler.setLevel(level);
    handler.setFormatter(
        new SimpleFormatter() {
          private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

          @Override
          public synchronized String format(final LogRecord lr) {
            return String.format(
                format,
                application != null
                    ? new Date(application.getClock().getTime() / 1000000L)
                    : new Date(),
                getLevelString(lr.getLevel()),
                lr.getMessage());
          }
        });
    internalLogger.addHandler(handler);
  }

  private static String getLevelString(final Level level) {
    if (level.intValue() > Level.WARNING.intValue()) {
      return ANSI_RED + "ERROR" + ANSI_RESET;
    } else if (level.intValue() > Level.INFO.intValue()) {
      return ANSI_YELLOW + "WARN" + ANSI_RESET;
    } else if (level.intValue() > Level.CONFIG.intValue()) {
      return ANSI_BLUE + "INFO" + ANSI_RESET;
    } else {
      return ANSI_CYAN + "TRACE" + ANSI_RESET;
    }
  }

  @Override
  public String getName() {
    return internalLogger.getName();
  }

  @Override
  public boolean isTraceEnabled() {
    return internalLogger.isLoggable(Level.FINEST);
  }

  @Override
  public void trace(final String msg) {
    internalLogger.log(Level.FINEST, msg);
  }

  @Override
  public void trace(final String format, final Object arg) {
    trace(format, new Object[] {arg});
  }

  @Override
  public void trace(final String format, final Object arg1, final Object arg2) {
    trace(format, new Object[] {arg1, arg2});
  }

  @Override
  public void trace(final String format, final Object... arguments) {
    trace(String.format(format, arguments));
  }

  @Override
  public void trace(final String msg, final Throwable t) {
    internalLogger.log(Level.FINEST, msg, t);
  }

  @Override
  public boolean isDebugEnabled() {
    return internalLogger.isLoggable(Level.FINE);
  }

  @Override
  public void debug(final String msg) {
    internalLogger.log(Level.FINE, msg);
  }

  @Override
  public void debug(final String format, final Object arg) {
    debug(format, new Object[] {arg});
  }

  @Override
  public void debug(final String format, final Object arg1, final Object arg2) {
    debug(format, new Object[] {arg1, arg2});
  }

  @Override
  public void debug(final String format, final Object... arguments) {
    debug(String.format(format, arguments));
  }

  @Override
  public void debug(final String msg, final Throwable t) {
    internalLogger.log(Level.FINE, msg, t);
  }

  @Override
  public boolean isInfoEnabled() {
    return internalLogger.isLoggable(Level.INFO);
  }

  @Override
  public void info(final String msg) {
    internalLogger.log(Level.INFO, msg);
  }

  @Override
  public void info(final String format, final Object arg) {
    info(format, new Object[] {arg});
  }

  @Override
  public void info(final String format, final Object arg1, final Object arg2) {
    info(format, new Object[] {arg1, arg2});
  }

  @Override
  public void info(final String format, final Object... arguments) {
    info(String.format(format, arguments));
  }

  @Override
  public void info(final String msg, final Throwable t) {
    internalLogger.log(Level.INFO, msg, t);
  }

  @Override
  public boolean isWarnEnabled() {
    return internalLogger.isLoggable(Level.WARNING);
  }

  @Override
  public void warn(final String msg) {
    internalLogger.log(Level.WARNING, msg);
  }

  @Override
  public void warn(final String format, final Object arg) {
    warn(format, new Object[] {arg});
  }

  @Override
  public void warn(final String format, final Object arg1, final Object arg2) {
    warn(format, new Object[] {arg1, arg2});
  }

  @Override
  public void warn(final String format, final Object... arguments) {
    warn(String.format(format, arguments));
  }

  @Override
  public void warn(final String msg, final Throwable t) {
    internalLogger.log(Level.WARNING, msg, t);
  }

  @Override
  public boolean isErrorEnabled() {
    return internalLogger.isLoggable(Level.SEVERE);
  }

  @Override
  public void error(final String msg) {
    internalLogger.log(Level.SEVERE, msg);
  }

  @Override
  public void error(final String format, final Object arg) {
    error(format, new Object[] {arg});
  }

  @Override
  public void error(final String format, final Object arg1, final Object arg2) {
    error(format, new Object[] {arg1, arg2});
  }

  @Override
  public void error(final String format, final Object... arguments) {
    error(String.format(format, arguments));
  }

  @Override
  public void error(final String msg, final Throwable t) {
    internalLogger.log(Level.SEVERE, msg, t);
  }
}
