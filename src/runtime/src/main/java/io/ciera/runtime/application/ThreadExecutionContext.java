package io.ciera.runtime.application;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.exceptions.EventTargetException;
import io.ciera.runtime.api.time.SystemClock;
import io.ciera.runtime.api.time.Timer;
import io.ciera.runtime.api.types.Duration;
import io.ciera.runtime.api.types.TimeStamp;
import io.ciera.runtime.application.task.GeneratedEvent;
import io.ciera.runtime.application.task.GeneratedEventToSelf;
import io.ciera.runtime.application.task.Halt;
import io.ciera.runtime.application.task.Task;
import io.ciera.runtime.time.EventTimer;

public class ThreadExecutionContext implements ExecutionContext, Runnable {

    private final String name;
    private final Application application;
    private final ExecutionMode executionMode;
    private final ModelIntegrityMode modelIntegrityMode;
    private final Queue<Task> tasks;

    private static int taskSequenceNumber = 1;

    public ThreadExecutionContext(String name, Application application) {
        this(name, application, ExecutionMode.INTERLEAVED, ModelIntegrityMode.STRICT);

    }

    public ThreadExecutionContext(String name, Application application, ExecutionMode executionMode,
            ModelIntegrityMode modelIntegrityMode) {
        this.name = name;
        this.application = application;
        this.executionMode = executionMode;
        this.modelIntegrityMode = modelIntegrityMode;
        this.tasks = new PriorityBlockingQueue<>();
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
                execute(new GeneratedEventToSelf(event, target));
            } else {
                execute(new GeneratedEvent(event, target));
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
            EventTimer timer = new EventTimer(this, event, target);
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
    public <E extends Event> Timer scheduleRecurringEvent(Class<E> eventType, EventTarget target, Duration delay,
            Duration period, Object... eventData) {
        try {
            Constructor<E> eventBuilder = eventType.getConstructor(Object[].class);
            Event event = eventBuilder.newInstance((Object) eventData);
            EventTimer timer = new EventTimer(this, event, target, period != null ? period : delay);
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
    public void halt() {
        execute(new Halt());
    }

    @Override
    @Deprecated
    public void delay(Duration delay) {
        application.getLogger().trace("DEL: delaying for %s", delay);
        try {
            Thread.sleep(delay.getValue() / 1000000l, (int) delay.getValue() % 1000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void addTask(Task newTask) {
        if (tasks.offer(newTask)) {
            notify();
        } else {
            application.getLogger().error("Could not add task to queue");
        }
    }

    @Override
    public void execute(Runnable command) {
        Task t = command instanceof Task ? (Task) command : new Task() {
            @Override
            public void run() {
                if (command != null) {
                    command.run();
                }
            }
        };
        t.setContext(this);
        addTask(t);
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
                    application.getExceptionHandler().handleError(e);
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

    @Override
    public Application getApplication() {
        return application;
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
        return application.getClock();
    }

    public static int nextSequenceNumber() {
        return taskSequenceNumber++;
    }

    @Override
    public String toString() {
        return name;
    }

}
