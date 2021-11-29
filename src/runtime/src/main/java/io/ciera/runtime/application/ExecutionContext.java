package io.ciera.runtime.application;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import io.ciera.runtime.application.task.GeneratedEvent;
import io.ciera.runtime.application.task.GeneratedEventToSelf;
import io.ciera.runtime.domain.InstancePopulation;
import io.ciera.runtime.exceptions.InstancePopulationException;
import io.ciera.runtime.types.Duration;
import io.ciera.runtime.types.TimeStamp;

public class ExecutionContext implements Runnable, Named {

    private final String name;
    private final Application application;
    private final Logger logger;
    private final ExceptionHandler exceptionHandler;
    private final InstancePopulation instancePopulation;
    private final ExecutionMode executionMode;
    private final ModelIntegrityMode modelIntegrityMode;

    private final SystemClock clock;
    private final Queue<Task> tasks;

    private int sequenceNumber;

    public ExecutionContext(String name, Application application, InstancePopulation instancePopulation) {
        this(name, application, instancePopulation, ExecutionMode.INTERLEAVED, ModelIntegrityMode.STRICT, false);

    }

    public ExecutionContext(String name, Application application, InstancePopulation instancePopulation,
            ExecutionMode executionMode, ModelIntegrityMode modelIntegrityMode, boolean enableSimulatedTime) {
        this.name = name;
        this.application = application;
        this.logger = application.getLogger();
        this.exceptionHandler = application.getExceptionHandler();
        this.instancePopulation = instancePopulation;
        this.executionMode = executionMode;
        this.modelIntegrityMode = modelIntegrityMode;
        this.tasks = new PriorityBlockingQueue<>();

        if (!enableSimulatedTime) {
            this.clock = new WallClock(this);
        } else {
            this.clock = new SimulatedClock(this);
        }

        this.sequenceNumber = 1;
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
            Event event = eventBuilder.newInstance((Object)data);
            if (toSelf) {
                addTask(new GeneratedEventToSelf(this, event, target));
            } else {
                addTask(new GeneratedEvent(this, event, target));
            }
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new InstancePopulationException("Could not generate event TODO", e);
        }
    }

    public synchronized void scheduleEvent(Event event, EventTarget target, Timer timer) {
        instancePopulation.removeTimer(timer);
        instancePopulation.addTimer(timer);
        clock.registerTimer(timer, event, target);
        notify();
    }

    public Timer scheduleEvent(Event event, EventTarget target, TimeStamp expiration, Duration period) {
        Timer timer = new Timer(event, target, expiration.getValue(), period.getValue());
        scheduleEvent(event, target, timer);
        return timer;
    }

    public Timer scheduleEvent(Event event, EventTarget target, TimeStamp expiration) {
        return scheduleEvent(event, target, expiration, new Duration(0l));
    }

    public Timer scheduleEvent(Event event, EventTarget target, Duration delay, Duration period) {
        TimeStamp expiration = TimeStamp.now(clock).add(delay).castTo(TimeStamp.class);
        return scheduleEvent(event, target, expiration, period);
    }

    public Timer scheduleEvent(Event event, EventTarget target, Duration delay) {
        return scheduleEvent(event, target, delay, new Duration(0l));
    }
    
    public boolean cancelTimer(Timer timer) {
        return clock.cancelTimer(timer);
    }

    private synchronized void addTask(Task newTask) {
        if (tasks.offer(newTask)) {
            notify();
        } else {
            logger.error("Could not add task to queue");
        }
    }

    public void addTask(Runnable genericTask) {
        addTask(new Task(this) {
            @Override
            public void run() {
                if (genericTask != null) {
                    genericTask.run();
                }
            }
        });
    }

    @Override
    public void run() {
        while (application.isRunning()) {

            // check to see if any timers are expired
            clock.checkTimers();

            // get the next task (if there are any)
            Task task = tasks.poll();

            if (task != null) {
                // execute the task
                try {
                    // TODO initialize transaction
                    task.run();
                    // TODO complete transaction (check integrity, persist instance pop)
                } catch (RuntimeException e) {
                    exceptionHandler.handle(e);
                }
            } else {
                // wait for something interesting to happen
                try {
                    if (clock.hasActiveTimers()) {
                        // wait for the next timer to expire
                        clock.waitForNextTimer();
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
        Thread t = new Thread(this, getName());
        t.start();
        return t;
    }

    @Override
    public String getName() {
        return name;
    }

    public Application getApplication() {
        return application;
    }

    public InstancePopulation getInstancePopulation() {
        return instancePopulation;
    }

    public ExecutionMode getExecutionMode() {
        return executionMode;
    }

    public ModelIntegrityMode getModelIntegrityMode() {
        return modelIntegrityMode;
    }

    public SystemClock getClock() {
        return clock;
    }

    protected int nextSequenceNumber() {
        return sequenceNumber++;
    }

    @Override
    public String toString() {
        return getName();
    }

}
