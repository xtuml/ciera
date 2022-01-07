package io.ciera.runtime.application;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.PriorityBlockingQueue;

import io.ciera.runtime.application.task.GeneratedEvent;
import io.ciera.runtime.application.task.GeneratedEventToSelf;
import io.ciera.runtime.application.task.Halt;
import io.ciera.runtime.exceptions.EventTargetException;
import io.ciera.runtime.types.Duration;
import io.ciera.runtime.types.TimeStamp;

public class ExecutionContext implements Runnable, Executor {

    private final String name;
    private final Application application;
    private final ExecutionMode executionMode;
    private final ModelIntegrityMode modelIntegrityMode;
    private final Queue<Task> tasks;

    private int taskSequenceNumber;

    public ExecutionContext(String name, Application application) {
        this(name, application, ExecutionMode.INTERLEAVED, ModelIntegrityMode.STRICT);

    }

    public ExecutionContext(String name, Application application, ExecutionMode executionMode,
            ModelIntegrityMode modelIntegrityMode) {
        this.name = name;
        this.application = application;
        this.executionMode = executionMode;
        this.modelIntegrityMode = modelIntegrityMode;
        this.tasks = new PriorityBlockingQueue<>();
        this.taskSequenceNumber = 1;
    }

    public <E extends Event> void generateEvent(Class<E> eventType, EventTarget target, Object... data) {
        generateEvent(eventType, target, false, data);

    }

    public <E extends Event> void generateEventToSelf(Class<E> eventType, EventTarget target, Object... data) {
        generateEvent(eventType, target, true, data);
    }

    private <E extends Event> void generateEvent(Class<E> eventType, EventTarget target, boolean toSelf,
            Object... data) {
        try {
            Constructor<E> eventBuilder = eventType.getConstructor(Object[].class);
            Event event = eventBuilder.newInstance((Object) data);
            if (toSelf) {
                addTask(new GeneratedEventToSelf(this, event, target));
            } else {
                addTask(new GeneratedEvent(this, event, target));
            }
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new EventTargetException(String.format("Could not generate event '%s'", eventType.getSimpleName()), e,
                    target, null);
        }
    }

    public <E extends Event> Timer scheduleEvent(Class<E> eventType, EventTarget target, Duration delay,
            Object... eventData) {
        try {
            Constructor<E> eventBuilder = eventType.getConstructor(Object[].class);
            Event event = eventBuilder.newInstance((Object) eventData);
            Timer timer = new Timer(this, event, target);
            timer.schedule(delay.getValue());
            return timer;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new EventTargetException(String.format("Could not schedule event '%s'", eventType.getSimpleName()), e,
                    target, null);
        }
    }

    public <E extends Event> Timer scheduleEvent(Class<E> eventType, EventTarget target, TimeStamp expiration,
            Object... eventData) {
        Duration delay = expiration.subtract(TimeStamp.now(getClock())).castTo(Duration.class);
        return scheduleEvent(eventType, target, delay, eventData);
    }

    public <E extends Event> Timer scheduleRecurringEvent(Class<E> eventType, EventTarget target, Duration delay,
            Duration period, Object... eventData) {
        try {
            Constructor<E> eventBuilder = eventType.getConstructor(Object[].class);
            Event event = eventBuilder.newInstance((Object) eventData);
            Timer timer = new Timer(this, event, target, period != null ? period : delay);
            timer.schedule(delay.getValue());
            return timer;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new EventTargetException(String.format("Could not schedule event '%s'", eventType.getSimpleName()), e,
                    target, null);
        }
    }

    public <E extends Event> Timer scheduleRecurringEvent(Class<E> eventType, EventTarget target, TimeStamp expiration,
            Duration period, Object... eventData) {
        Duration delay = expiration.subtract(TimeStamp.now(getClock())).castTo(Duration.class);
        return scheduleRecurringEvent(eventType, target, delay, period, eventData);
    }

    public void halt() {
        addTask(new Halt(this));
    }

    @Deprecated
    public void delay(Duration delay) {
        getLogger().trace("DEL: delaying for %s", delay);
        try {
            Thread.sleep(delay.getValue() / 1000000l, (int) delay.getValue() % 1000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void addTask(Task newTask) {
        if (tasks.offer(newTask)) {
            notify();
        } else {
            application.getLogger().error("Could not add task to queue");
        }
    }

    @Override
    public void execute(Runnable command) {
        addTask(new Task(this) {
            @Override
            public void run() {
                if (command != null) {
                    command.run();
                }
            }
        });
    }

    @Override
    public void run() {
        while (application.isRunning()) {

            // check to see if any expired timers need to be added to the task queue
            getClock().checkTimers(this);

            // get the next task (if there are any)
            Task task = tasks.poll();

            if (task != null) {
                // execute the task
                try {
                    task.run();
                } catch (RuntimeException e) {
                    application.getExceptionHandler().handle(e);
                }
            } else {
                // wait for something interesting to happen
                try {
                    if (getClock().hasScheduledTimers(this)) {
                        // wait for the next timer to expire
                        getClock().waitForNextTimer(this);
                    } else {
                        // wait indefinitely for an external signal or for a
                        // timer to be scheduled in this context by another thread
                        synchronized (this) {
                            wait();
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

    public Application getApplication() {
        return application;
    }

    public ExecutionMode getExecutionMode() {
        return executionMode;
    }

    public ModelIntegrityMode getModelIntegrityMode() {
        return modelIntegrityMode;
    }

    public SystemClock getClock() {
        return application.getClock();
    }

    public Logger getLogger() {
        return application.getLogger();
    }

    protected int nextSequenceNumber() {
        return taskSequenceNumber++;
    }

    @Override
    public String toString() {
        return name;
    }

}
