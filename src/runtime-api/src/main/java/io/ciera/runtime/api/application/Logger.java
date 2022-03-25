package io.ciera.runtime.api.application;

/**
 * This interface is based on the Logger interface from the SLF4J project. The
 * goal is to create an interface without external dependencies but can be used
 * with a SLF4J compliant logger.
 * 
 * @see org.slf4j.Logger
 */
public interface Logger {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public String getName();

    public boolean isTraceEnabled();

    public void trace(String msg);

    public void trace(String format, Object arg);

    public void trace(String format, Object arg1, Object arg2);

    public void trace(String format, Object... arguments);

    public void trace(String msg, Throwable t);

    public boolean isDebugEnabled();

    public void debug(String msg);

    public void debug(String format, Object arg);

    public void debug(String format, Object arg1, Object arg2);

    public void debug(String format, Object... arguments);

    public void debug(String msg, Throwable t);

    public boolean isInfoEnabled();

    public void info(String msg);

    public void info(String format, Object arg);

    public void info(String format, Object arg1, Object arg2);

    public void info(String format, Object... arguments);

    public void info(String msg, Throwable t);

    public boolean isWarnEnabled();

    public void warn(String msg);

    public void warn(String format, Object arg);

    public void warn(String format, Object... arguments);

    public void warn(String format, Object arg1, Object arg2);

    public void warn(String msg, Throwable t);

    public boolean isErrorEnabled();

    public void error(String msg);

    public void error(String format, Object arg);

    public void error(String format, Object arg1, Object arg2);

    public void error(String format, Object... arguments);

    public void error(String msg, Throwable t);

}
