package io.ciera.runtime.summit2.application;

import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/*
 * This default logger implements the generic interface and is based internally
 * on the JUL logging system. It's default log level is INFO so debug and trace
 * logs will be ignored by default.
 */
public class DefaultLogger implements Logger {

    java.util.logging.Logger internalLogger;

    public DefaultLogger(String name) {
        this(name, Level.INFO);
    }

    public DefaultLogger(String name, Level level) {
        internalLogger = java.util.logging.Logger.getLogger(name);
        internalLogger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter() {
            private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

            @Override
            public synchronized String format(LogRecord lr) {
                return String.format(format, new Date(lr.getMillis()), lr.getLevel().getLocalizedName(),
                        lr.getMessage());
            }
        });
        internalLogger.addHandler(handler);
        internalLogger.setLevel(level);
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
    public void trace(String msg) {
        internalLogger.log(Level.FINEST, msg);
    }

    @Override
    public void trace(String format, Object arg) {
        trace(format, arg);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        trace(format, arg1, arg2);
    }

    @Override
    public void trace(String format, Object... arguments) {
        trace(String.format(format, arguments));
    }

    @Override
    public void trace(String msg, Throwable t) {
        internalLogger.log(Level.FINEST, msg, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return internalLogger.isLoggable(Level.FINE);
    }

    @Override
    public void debug(String msg) {
        internalLogger.log(Level.FINE, msg);
    }

    @Override
    public void debug(String format, Object arg) {
        debug(format, arg);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        debug(format, arg1, arg2);
    }

    @Override
    public void debug(String format, Object... arguments) {
        debug(String.format(format, arguments));
    }

    @Override
    public void debug(String msg, Throwable t) {
        internalLogger.log(Level.FINE, msg, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return internalLogger.isLoggable(Level.INFO);
    }

    @Override
    public void info(String msg) {
        internalLogger.log(Level.INFO, msg);
    }

    @Override
    public void info(String format, Object arg) {
        info(format, arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        info(format, arg1, arg2);
    }

    @Override
    public void info(String format, Object... arguments) {
        info(String.format(format, arguments));
    }

    @Override
    public void info(String msg, Throwable t) {
        internalLogger.log(Level.INFO, msg, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return internalLogger.isLoggable(Level.WARNING);
    }

    @Override
    public void warn(String msg) {
        internalLogger.log(Level.WARNING, msg);
    }

    @Override
    public void warn(String format, Object arg) {
        warn(format, arg);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        warn(format, arg1, arg2);
    }

    @Override
    public void warn(String format, Object... arguments) {
        warn(String.format(format, arguments));
    }

    @Override
    public void warn(String msg, Throwable t) {
        internalLogger.log(Level.WARNING, msg, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return internalLogger.isLoggable(Level.SEVERE);
    }

    @Override
    public void error(String msg) {
        internalLogger.log(Level.SEVERE, msg);
    }

    @Override
    public void error(String format, Object arg) {
        error(format, arg);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        error(format, arg1, arg2);
    }

    @Override
    public void error(String format, Object... arguments) {
        error(String.format(format, arguments));
    }

    @Override
    public void error(String msg, Throwable t) {
        internalLogger.log(Level.SEVERE, msg, t);
    }

}