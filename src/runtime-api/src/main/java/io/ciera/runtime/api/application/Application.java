package io.ciera.runtime.api.application;

import java.util.Collection;
import java.util.List;

import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.time.SystemClock;

public interface Application {

    public SystemClock getClock();

    public void setClock(SystemClock clock);

    public void setup();

    public void initialize();

    public void start();

    public void stop();

    public List<? extends ExecutionContext> getContexts();

    public ExecutionContext defaultContext();

    public void addContext(ExecutionContext context);

    public Collection<Domain> getDomains();

    public <T extends Domain> T getDomain(Class<T> cls);

    public void addDomain(Domain domain);

    public Logger getLogger();

    public void setLogger(Logger logger);

    public ExceptionHandler getExceptionHandler();

    public void setExceptionHandler(ExceptionHandler exceptionHandler);

    public String[] getArgs();

    public boolean isRunning();

}
