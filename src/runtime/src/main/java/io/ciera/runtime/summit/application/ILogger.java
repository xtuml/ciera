package io.ciera.runtime.summit.application;

public interface ILogger {

    public void debug(CharSequence content);

    public void debug(CharSequence content, Throwable error);

    public void debug(Throwable error);

    public void debug(String format, Object ... args);

    public void error(CharSequence content);

    public void error(CharSequence content, Throwable error);

    public void error(Throwable error);

    public void error(String format, Object ... args);

    public void info(CharSequence content);

    public void info(CharSequence content, Throwable error);

    public void info(Throwable error);

    public void info(String format, Object ... args);

    public boolean isDebugEnabled();

    public boolean isErrorEnabled();

    public boolean isInfoEnabled();

    public boolean isWarnEnabled();

    public void warn(CharSequence content);

    public void warn(CharSequence content, Throwable error);

    public void warn(Throwable error);

    public void warn(String format, Object ... args);

}
