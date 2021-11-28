package io.ciera.runtime.application;

import java.util.ArrayList;
import java.util.List;

import io.ciera.runtime.domain.Domain;

public abstract class Application implements Named {

    private String name;
    private boolean running;
    private final List<ExecutionContext> contexts;
    private final List<Domain> domains;
    private Logger logger;
    private ExceptionHandler exceptionHandlerr;
    private final String[] args;

    public Application(String name, String[] args) {
        this.name = name;
        this.contexts = new ArrayList<>();
        this.domains = new ArrayList<>();
        this.args = args;
        this.logger = new DefaultLogger(getName());
        this.exceptionHandlerr = new DefaultExceptionHandler();
    }

    @Override
    public String getName() {
        return name;
    }

    public abstract void setup();

    public abstract void initialize();

    public void start() {
        running = true;
        if (contexts.size() == 1) {
            contexts.get(0).run();
        } else {
            contexts.forEach(context -> context.start());
        }
    }

    public synchronized void stop() {
        running = false;
    }

    public List<ExecutionContext> getContexts() {
        return contexts;
    }

    public ExecutionContext defaultContext() {
        return !contexts.isEmpty() ? contexts.get(0) : null;
    }

    public void addContext(ExecutionContext context) {
        this.contexts.add(context);
    }

    public List<Domain> getDomains() {
        return domains;
    }

    public void addDomain(Domain domain) {
        this.domains.add(domain);
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public ExceptionHandler getExceptionHandler() {
        return exceptionHandlerr;
    }

    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandlerr = exceptionHandler;
    }

    public String[] getArgs() {
        return args;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public String toString() {
        return getName();
    }

}
