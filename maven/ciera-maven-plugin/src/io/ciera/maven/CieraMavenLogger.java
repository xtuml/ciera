package io.ciera.maven;

import org.apache.maven.plugin.logging.Log;

import io.ciera.runtime.summit.application.ILogger;

public class CieraMavenLogger implements ILogger {
    
    private Log logger;

    public CieraMavenLogger(Log logger) {
        this.logger = logger;
    }

    public void debug(CharSequence content) {
        logger.debug(content);
    }

    public void debug(CharSequence content, Throwable error) {
        logger.debug(content, error);
    }

    public void debug(Throwable error) {
        logger.debug(error);
    }

    public void debug(String format, Object ... args) {
        logger.debug(String.format(format, args));
    }

    public void error(CharSequence content) {
        logger.error(content);
    }

    public void error(CharSequence content, Throwable error) {
        logger.error(content, error);
    }

    public void error(Throwable error) {
        logger.error(error);
    }

    public void error(String format, Object ... args) {
        logger.error(String.format(format, args));
    }

    public void info(CharSequence content) {
        logger.info(content);
    }

    public void info(CharSequence content, Throwable error) {
        logger.info(content, error);
    }

    public void info(Throwable error) {
        logger.info(error);
    }

    public void info(String format, Object ... args) {
        logger.info(String.format(format, args));
    }

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    public void warn(CharSequence content) {
        logger.warn(content);
    }

    public void warn(CharSequence content, Throwable error) {
        logger.warn(content, error);
    }

    public void warn(Throwable error) {
        logger.warn(error);
    }

    public void warn(String format, Object ... args) {
        logger.warn(String.format(format, args));
    }
}
