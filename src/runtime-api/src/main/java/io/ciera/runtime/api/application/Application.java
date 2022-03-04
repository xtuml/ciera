package io.ciera.runtime.api.application;

import java.util.Collection;
import java.util.stream.Stream;

import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.time.SystemClock;

public interface Application {

    public SystemClock getClock();

    public void setClock(SystemClock clock);

    public void setup();

    public void initialize();

    public void start();

    public void stop();

    public Collection<? extends ExecutionContext> getContexts();

    public <T extends ExecutionContext> T getContext(String name);

    public ExecutionContext defaultContext();

    public void addContext(ExecutionContext context);

    public Collection<Domain> getDomains();

    public Domain getDomain(String domainName);

    public void addDomain(Domain domain);
    
    public Stream<Domain> findDomains(String... domainNames);

    public Logger getLogger();

    public void setLogger(Logger logger);

    public ExceptionHandler getExceptionHandler();

    public void setExceptionHandler(ExceptionHandler exceptionHandler);

    public boolean isRunning();

}
