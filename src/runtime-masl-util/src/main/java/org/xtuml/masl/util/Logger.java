package org.xtuml.masl.util;

import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.types.TimeStamp;

public class Logger {
    
    private final Domain domain;

    public Logger(Domain domain) {
        this.domain = domain;
    }

    public static enum Priority {
        Fatal, Critical, Error, Warning, Notice, Information, Debug, Trace;
    }

    public void log(final Priority priority, final String logger, final String message) {
        System.out.printf("%s %s : %s : %s\n", TimeStamp.now(), priority, logger, message);
    }

    public void log(final Priority priority, final String message) {
        log(priority, domain.getName(), message);
    }

    public void trace(final String logger, final String message) {
        log(Priority.Trace, logger, message);
    }

    public void trace(final String message) {
        log(Priority.Trace, message);
    }

    public void debug(final String logger, final String message) {
        log(Priority.Debug, logger, message);
    }

    public void debug(final String message) {
        log(Priority.Debug, message);
    }

    public void information(final String logger, final String message) {
        log(Priority.Information, logger, message);
    }

    public void information(final String message) {
        log(Priority.Information, message);
    }

    public void notice(final String logger, final String message) {
        log(Priority.Notice, logger, message);
    }

    public void notice(final String message) {
        log(Priority.Notice, message);
    }

    public void warning(final String logger, final String message) {
        log(Priority.Warning, logger, message);
    }

    public void warning(final String message) {
        log(Priority.Warning, message);
    }

    public void error(final String logger, final String message) {
        log(Priority.Error, logger, message);
    }

    public void error(final String message) {
        log(Priority.Error, message);
    }

    public void critical(final String logger, final String message) {
        log(Priority.Critical, logger, message);
    }

    public void critical(final String message) {
        log(Priority.Critical, message);
    }

    public void fatal(final String logger, final String message) {
        log(Priority.Fatal, logger, message);
    }

    public void fatal(final String message) {
        log(Priority.Fatal, message);
    }

    public void printLoggers() {
        throw new UnsupportedOperationException();
    }

    public void setLogLevel(final String logger, final Priority priority) {
        throw new UnsupportedOperationException();
    }

    public void setLogLevel(final Priority priority) {
        throw new UnsupportedOperationException();
    }

}