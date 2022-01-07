package io.ciera.runtime.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.ciera.runtime.domain.Domain;

public abstract class Application {

    private final String name;
    private final List<ExecutionContext> contexts;
    private final Map<Class<?>, Domain> domains;
    private final String[] args;

    private SystemClock clock;
    private Logger logger;
    private ExceptionHandler exceptionHandler;

    private boolean running;

    public Application(String name, String[] args) {
        this.name = name;
        this.contexts = new ArrayList<>();
        this.domains = new HashMap<>();
        this.args = args;
        this.clock = new WallClock();
        this.logger = new DefaultLogger(name + "Logger", this);
        this.exceptionHandler = new DefaultExceptionHandler();
    }

    public SystemClock getClock() {
        return clock;
    }

    public void setClock(SystemClock clock) {
        this.clock = clock;
    }

    public void setup() {
        // create default execution context
        addContext(new ExecutionContext("DefaultExecutionContext", this));
    }

    public void initialize() {
        for (Domain domain : getDomains()) {
            domain.getContext().execute(() -> domain.initialize());
        }
    }

    public void start() {
        running = true;
        if (contexts.size() == 1) {
            // run the single context in the main thread
            contexts.get(0).run();
        } else if (contexts.size() > 1) {
            // run each context in its own thread and wait for them all to complete
            contexts.stream().map(context -> context.start()).forEach(thread -> {
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

    public Collection<Domain> getDomains() {
        return domains.values();
    }

    @SuppressWarnings("unchecked")
    public <T extends Domain> T getDomain(Class<T> cls) {
        return (T) domains.get(cls);
    }

    public void addDomain(Domain domain) {
        this.domains.put(domain.getClass(), domain);
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public String[] getArgs() {
        return args;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public String toString() {
        return name;
    }

}
