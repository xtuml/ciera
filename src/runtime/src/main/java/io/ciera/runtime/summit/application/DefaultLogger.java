package io.ciera.runtime.summit.application;

import java.io.OutputStream;
import java.io.PrintStream;

public class DefaultLogger implements ILogger {
    
    private PrintStream out;
    private PrintStream err;
    
    public DefaultLogger() {
        out = System.out;
        err = System.err;
    }
    
    public DefaultLogger(OutputStream out, OutputStream err) {
        this.out = new PrintStream(out);
        this.err = new PrintStream(err);
    }
    
    @Override
    public void debug(CharSequence content) {
        info(content);
    }

    @Override
    public void debug(CharSequence content, Throwable error) {
        info(content, error);
    }

    @Override
    public void debug(Throwable error) {
        info(error);
    }

    @Override
    public void debug(String format, Object... args) {
        info(format, args);
    }

    @Override
    public void error(CharSequence content) {
        err.println(content);
    }

    @Override
    public void error(CharSequence content, Throwable error) {
        err.println(content);
        err.println(error.getMessage());
        error.printStackTrace(err);
    }

    @Override
    public void error(Throwable error) {
        err.println(error.getMessage());
        error.printStackTrace(err);
    }

    @Override
    public void error(String format, Object... args) {
        err.printf(format + "\n", args);
    }

    @Override
    public void info(CharSequence content) {
        out.println(content);
    }

    @Override
    public void info(CharSequence content, Throwable error) {
        out.println(content);
        out.println(error.getMessage());
        error.printStackTrace(out);
    }

    @Override
    public void info(Throwable error) {
        out.println(error.getMessage());
        error.printStackTrace(out);
    }

    @Override
    public void info(String format, Object... args) {
        out.printf(format + "\n", args);
    }

    @Override
    public boolean isDebugEnabled() {
        return false;  // debug disabled by default
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void warn(CharSequence content) {
        info(content);
    }

    @Override
    public void warn(CharSequence content, Throwable error) {
        info(content, error);
    }

    @Override
    public void warn(Throwable error) {
        info(error);
    }

    @Override
    public void warn(String format, Object... args) {
        info(format, args);
    }

}
