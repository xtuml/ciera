package io.ciera.runtime.application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.application.Logger;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.PersistentDomain;
import io.ciera.runtime.api.exceptions.EventTargetException;
import io.ciera.runtime.api.time.SystemClock;
import io.ciera.runtime.api.time.Timer;
import io.ciera.runtime.api.types.Duration;
import io.ciera.runtime.api.types.TimeStamp;
import io.ciera.runtime.application.task.GeneratedEvent;
import io.ciera.runtime.application.task.GeneratedEventToSelf;
import io.ciera.runtime.application.task.GenericTask;
import io.ciera.runtime.application.task.Task;
import io.ciera.runtime.time.AbstractTimer;
import io.ciera.runtime.time.EventTimer;
import io.ciera.runtime.time.GenericTimer;

public class ThreadExecutionContext implements ExecutionContext, Runnable {

    private final String name;
    private final ExecutionMode executionMode;
    private final ModelIntegrityMode modelIntegrityMode;
    private final Queue<Task> tasks;
    private Task currentTask;

    public ThreadExecutionContext(String name) {
        this(name, ExecutionMode.INTERLEAVED, ModelIntegrityMode.STRICT);

    }

    public ThreadExecutionContext(String name, ExecutionMode executionMode, ModelIntegrityMode modelIntegrityMode) {
        this.name = name;
        this.executionMode = executionMode;
        this.modelIntegrityMode = modelIntegrityMode;
        this.tasks = new PriorityBlockingQueue<>();
        this.currentTask = null;
    }

    @Override
    public String getName() {
        return name;
    }

    public Stream<Runnable> getTasks() {
        return tasks.stream().map(Runnable.class::cast);
    }

    @Override
    public <E extends Event> void generateEvent(Class<E> eventType, EventTarget target, Object... data) {
        generateEvent(eventType, target, false, data);

    }

    @Override
    public <E extends Event> void generateEventToSelf(Class<E> eventType, EventTarget target, Object... data) {
        generateEvent(eventType, target, true, data);
    }

    private <E extends Event> void generateEvent(Class<E> eventType, EventTarget target, boolean toSelf,
            Object... data) {
        try {
            Constructor<E> eventBuilder = eventType.getConstructor(Object[].class);
            Event event = eventBuilder.newInstance((Object) data);
            if (toSelf) {
                target.getContext().execute(new GeneratedEventToSelf(event, target));
            } else {
                target.getContext().execute(new GeneratedEvent(event, target, getExecutionMode()));
            }
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new EventTargetException(String.format("Could not generate event '%s'", eventType.getSimpleName()), e,
                    target, null);
        }
    }

    @Override
    public <E extends Event> Timer scheduleEvent(Class<E> eventType, EventTarget target, Duration delay,
            Object... eventData) {
        try {
            Constructor<E> eventBuilder = eventType.getConstructor(Object[].class);
            Event event = eventBuilder.newInstance((Object) eventData);
            Timer timer = new EventTimer(this, event, target);
            timer.schedule(delay.getValue());
            return timer;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new EventTargetException(String.format("Could not schedule event '%s'", eventType.getSimpleName()), e,
                    target, null);
        }
    }

    @Override
    public <E extends Event> Timer scheduleEvent(Class<E> eventType, EventTarget target, TimeStamp expiration,
            Object... eventData) {
        Duration delay = expiration.subtract(TimeStamp.now(getClock()));
        return scheduleEvent(eventType, target, delay, eventData);
    }

    @Override
    public Timer scheduleAction(Duration delay, Runnable action) {
        Timer timer = new GenericTimer(this, action);
        timer.schedule(delay.getValue());
        return timer;
    }

    @Override
    public Timer scheduleAction(TimeStamp expiration, Runnable action) {
        Duration delay = expiration.subtract(TimeStamp.now(getClock()));
        return scheduleAction(delay, action);
    }

    @Override
    public <E extends Event> Timer scheduleRecurringEvent(Class<E> eventType, EventTarget target, Duration delay,
            Duration period, Object... eventData) {
        try {
            Constructor<E> eventBuilder = eventType.getConstructor(Object[].class);
            Event event = eventBuilder.newInstance((Object) eventData);
            AbstractTimer timer = new EventTimer(this, event, target, period != null ? period : delay);
            timer.schedule(delay.getValue());
            return timer;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new EventTargetException(String.format("Could not schedule event '%s'", eventType.getSimpleName()), e,
                    target, null);
        }
    }

    @Override
    public <E extends Event> Timer scheduleRecurringEvent(Class<E> eventType, EventTarget target, TimeStamp expiration,
            Duration period, Object... eventData) {
        Duration delay = expiration.subtract(TimeStamp.now(getClock()));
        return scheduleRecurringEvent(eventType, target, delay, period, eventData);
    }

    @Override
    public Timer scheduleRecurringAction(Duration delay, Duration period, Runnable action) {
        Timer timer = new GenericTimer(this, action, period);
        timer.schedule(delay.getValue());
        return timer;
    }

    @Override
    public Timer scheduleRecurringAction(TimeStamp expiration, Duration period, Runnable action) {
        Duration delay = expiration.subtract(TimeStamp.now(getClock()));
        return scheduleRecurringAction(delay, period, action);
    }

    @Override
    public void halt() {
        execute(() -> getApplication().stop());
    }

    @Override
    @Deprecated
    public void delay(Duration delay) {
        getLogger().trace("DEL: delaying for %s", delay);
        try {
            Thread.sleep(delay.getValue() / 1000000l, (int) delay.getValue() % 1000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void addTask(Task newTask) {
        newTask.setParent(currentTask);
        if (tasks.offer(newTask)) {
            notify();
        } else {
            getLogger().error("Could not add task to queue");
        }
    }

    @Override
    public void execute(Runnable command) {
        if (command != null) {
            Task t = command instanceof Task ? (Task) command : new GenericTask(command);
            addTask(t);
        }
    }

    private void commitTransaction(final String storeName) {
        // persist the instance population
        persistPopulation(storeName);

        // check integrity if in strict mode
        if (modelIntegrityMode == ModelIntegrityMode.STRICT) {
            checkIntegrity();
        }
    }

    private void checkIntegrity() {
        // TODO
    }

    private void loadPopulation(final String storeName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(storeName))) {
            getLogger().trace("Loading instance population...");
            for (PersistentDomain domain : getApplication().getDomains().stream()
                    .filter(d -> this.equals(d.getContext())).filter(PersistentDomain.class::isInstance)
                    .map(PersistentDomain.class::cast).sorted(Comparator.comparing(Domain::getName))
                    .collect(Collectors.toList())) {
                domain.load(in);
            }
        } catch (FileNotFoundException e) {
            // ignore if the file doesn't exist
        } catch (IOException | ClassNotFoundException e) {
            getLogger().error("Failed to load instance population", e);
            getApplication().getExceptionHandler().handleError(new RuntimeException(e));
        }
    }

    private void persistPopulation(final String storeName) {
        // persist instance population
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(storeName))) {
            getLogger().trace("Dumping instance population...");
            for (PersistentDomain domain : getApplication().getDomains().stream()
                    .filter(d -> this.equals(d.getContext())).filter(PersistentDomain.class::isInstance)
                    .map(PersistentDomain.class::cast).sorted(Comparator.comparing(Domain::getName))
                    .collect(Collectors.toList())) {
                domain.persist(out);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void run() {
        // load instance population
        final String storeName = System.getProperty("io.ciera.runtime.objectStore");
        if (storeName != null && System.getProperty("io.ciera.runtime.coldStart") == null) {
            loadPopulation(storeName);
        }

        // main loop
        while (getApplication().isRunning()) {

            // check to see if any expired timers need to be added to the task queue
            getClock().checkTimers(this);

            // get the next task (if there are any)
            currentTask = tasks.poll();

            if (currentTask != null) {
                try {
                    // execute the task
                    currentTask.run();

                    // Commit the transaction
                    // A transaction is committed after every task (excluding generic execution) in
                    // interleaved mode.
                    // In sequential mode, the transaction is complete when the queue is empty or
                    // the next task is a primary task.
                    if ((executionMode == ExecutionMode.INTERLEAVED && !(currentTask instanceof GenericTask)
                            && storeName != null)
                            || (executionMode == ExecutionMode.SEQUENTIAL
                                    && (tasks.isEmpty() || tasks.peek().getParent() == null) && storeName != null)) {
                        commitTransaction(storeName);
                    }

                    // check integrity if the task queue is empty and in relaxed model integrity
                    // mode
                    if (modelIntegrityMode == ModelIntegrityMode.RELAXED && tasks.isEmpty()) {
                        checkIntegrity();
                    }
                } catch (RuntimeException e) {
                    getApplication().getExceptionHandler().handleError(e);
                } finally {
                    currentTask = null;
                }
            } else {
                // wait for something interesting to happen
                try {
                    if (getClock().hasScheduledTimers(this)) {
                        // wait for the next timer to expire
                        getClock().waitForNextTimer(this);
                    } else {
                        if (System.getProperty("io.ciera.runtime.haltWhenIdle") != null) {
                            getApplication().stop();
                        } else {
                            // wait indefinitely for an external signal or for a
                            // timer to be scheduled in this context by another thread
                            synchronized (this) {
                                wait();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    // woken up by external signal or new timer
                }
            }

        }
    }

    public Thread start() {
        Thread t = new Thread(this, name);
        t.start();
        return t;
    }

    @Override
    public ExecutionMode getExecutionMode() {
        return executionMode;
    }

    @Override
    public ModelIntegrityMode getModelIntegrityMode() {
        return modelIntegrityMode;
    }

    @Override
    public SystemClock getClock() {
        return getApplication().getClock();
    }

    private Logger getLogger() {
        return getApplication().getLogger();
    }

    @Override
    public Application getApplication() {
        return Application.getInstance();
    }

    @Override
    public String toString() {
        return name;
    }

}
