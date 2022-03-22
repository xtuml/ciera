package io.ciera.runtime.application;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.Stream;

import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.application.ExceptionHandler;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.application.Logger;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.time.SystemClock;
import io.ciera.runtime.application.task.DomainInitialization;
import io.ciera.runtime.time.WallClock;

public class BaseApplication implements Application {

    private final String name;
    private final Map<String, ThreadExecutionContext> contexts;
    private final Map<String, Domain> domains;

    private SystemClock clock;
    private Logger logger;
    private ExceptionHandler exceptionHandler;

    private boolean running;

    public BaseApplication(String name) {
        this.name = name;
        this.contexts = new HashMap<>();
        this.domains = new HashMap<>();
        this.clock = new WallClock();
        this.logger = new DefaultLogger(name + "Logger", this);
        this.exceptionHandler = new DefaultExceptionHandler();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public SystemClock getClock() {
        return clock;
    }

    @Override
    public void setClock(SystemClock clock) {
        this.clock = clock;
    }

    @Override
    public void setup() {
        // create default execution context
        addContext(new ThreadExecutionContext("DefaultExecutionContext"));
    }

    @Override
    public void initialize() {
        for (Domain domain : getDomains()) {
            domain.getContext().execute(new DomainInitialization(domain));
        }
    }

    @Override
    public void start() {
        running = true;
        if (contexts.size() == 1) {
            // run the single context in the main thread
            contexts.values().stream().findAny().orElseThrow().run();
        } else if (contexts.size() > 1) {
            // run each context in its own thread and wait for them all to complete
            contexts.values().stream().map(context -> context.start()).forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    logger.error("Main thread interrupted while waiting", e);
                    System.exit(1);
                }
            });
        } else {
            throw new IllegalStateException("Cannot run application with no execution contexts");
        }
    }

    @Override
    public synchronized void stop() {
        running = false;
    }

    @Override
    public Collection<ThreadExecutionContext> getContexts() {
        return contexts.values();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ThreadExecutionContext getContext(String name) {
        return contexts.get(name);
    }

    @Override
    public ExecutionContext defaultContext() {
        return contexts.values().stream().findAny().orElse(null);
    }

    @Override
    public void addContext(ExecutionContext context) {
        this.contexts.put(context.getName(), (ThreadExecutionContext) context);
    }

    @Override
    public Collection<Domain> getDomains() {
        return domains.values();
    }

    @Override
    public Domain getDomain(String domainName) {
        return Optional.ofNullable(domains.get(domainName)).orElseThrow();
    }

    @Override
    public void addDomain(Domain domain) {
        domains.put(domain.getName(), domain);
    }

    @Override
    public Stream<Domain> findDomains(String... domainNames) {
        List<String> domains = Arrays.asList(domainNames);
        return ServiceLoader.load(Domain.class).stream().map(ServiceLoader.Provider::get)
                .filter(p -> domains.stream().anyMatch(d -> p.getClass().getSimpleName().endsWith(d)));
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    @Override
    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public String toString() {
        return name;
    }

}
