package io.ciera.runtime.application;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import io.ciera.runtime.application.task.GeneratedEvent;
import io.ciera.runtime.application.task.GeneratedEventToSelf;
import io.ciera.runtime.domain.InstancePopulation;
import io.ciera.runtime.exceptions.InstancePopulationException;
import io.ciera.runtime.types.Duration;
import io.ciera.runtime.types.TimeStamp;
import io.ciera.runtime.types.TimerHandle;
import io.ciera.runtime.types.UniqueId;

public class ExecutionContext implements Runnable {

    private final int id;
    private final Application application;
    private final InstancePopulation instancePopulation;
    private final ExecutionMode executionMode;
    private final ModelIntegrityMode modelIntegrityMode;

    private final SystemClock clock;
    private final Thread clockThread;
    private final BlockingQueue<Task> tasks;

    private int sequenceNumber;

    public ExecutionContext(int id, Application application, InstancePopulation instancePopulation) {
        this(id, application, instancePopulation, ExecutionMode.INTERLEAVED, ModelIntegrityMode.STRICT, 1000000l,
                false);

    }

    public ExecutionContext(int id, Application application, InstancePopulation instancePopulation,
            ExecutionMode executionMode, ModelIntegrityMode modelIntegrityMode, long tickDuration,
            boolean enableSimulatedTime) {
        this.id = id;
        this.application = application;
        this.instancePopulation = instancePopulation;
        this.executionMode = executionMode;
        this.modelIntegrityMode = modelIntegrityMode;
        this.tasks = new PriorityBlockingQueue<>();

        if (!enableSimulatedTime) {
            this.clock = new WallClock(this, tickDuration);
            this.clockThread = new Thread((WallClock) this.clock, "Clock: " + getName());
        } else {
            this.clock = null;
            this.clockThread = null;
            // TODO create simulated clock
        }

        this.sequenceNumber = 1;
    }

    public String getName() {
        return "ExecutionContext-" + getId();
    }

    public int getId() {
        return id;
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

    public <E extends Event> void generateEvent(Class<E> eventType, EventTarget target, Object... data) {
        generateEvent(eventType, target, false, data);

    }

    public <E extends Event> void generateEventToSelf(Class<E> eventType, EventTarget target, Object... data) {
        generateEvent(eventType, target, true, data);
    }

    private <E extends Event> void generateEvent(Class<E> eventType, EventTarget target, boolean toSelf, Object... data) {
        try {
            Constructor<E> eventBuilder;
            eventBuilder = eventType.getConstructor(UniqueId.class, Object[].class);
            Event event = eventBuilder.newInstance(target.getTargetHandle(), (Object) data);
            generateEvent(event, target, toSelf);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new InstancePopulationException("Could not generate event TODO", e);
        }
    }

    private void generateEvent(Event event, EventTarget target, boolean toSelf) {
        if (toSelf) {
            addTask(new GeneratedEventToSelf(this, event, target));

        } else {
            addTask(new GeneratedEvent(this, event, target));
        }
    }

    public TimerHandle scheduleEvent(Event event, EventTarget target, Timer timer) {
        instancePopulation.removeTimer(timer.getTimerHandle());
        instancePopulation.addTimer(timer);
        clock.registerTimer(timer, event, target);
        return timer.getTimerHandle();
    }

    public TimerHandle scheduleEvent(Event event, EventTarget target, TimeStamp expiration, Duration period) {
        Timer timer = new Timer(event.getEventHandle(), target.getTargetHandle(), expiration.getValue(),
                period.getValue());
        return scheduleEvent(event, target, timer);
    }

    public TimerHandle scheduleEvent(Event event, EventTarget target, TimeStamp expiration) {
        return scheduleEvent(event, target, expiration, new Duration(0l));
    }

    public TimerHandle scheduleEvent(Event event, EventTarget target, Duration delay, Duration period) {
        TimeStamp expiration = TimeStamp.now(clock).add(delay).castTo(TimeStamp.class);
        return scheduleEvent(event, target, expiration, period);
    }

    public TimerHandle scheduleEvent(Event event, EventTarget target, Duration delay) {
        return scheduleEvent(event, target, delay, new Duration(0l));
    }

    public void addTask(Task newTask) {
        tasks.offer(newTask);
    }

    public void addTask(Runnable genericTask) {
        tasks.offer(new Task(this) {
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
        // start the clock
        if (this.clockThread != null) {
            this.clockThread.start();
        }
        while (application.isRunning()) {
            try {
                Task task = tasks.take();
                try {
                    // TODO initialize transaction
                    task.run();
                    // TODO complete transaction
                } catch (RuntimeException e) {
                    // TODO invoke exception handler
                    getApplication().getExceptionHandler().handle(e);
                }
            } catch (InterruptedException e) {
                // TODO handle interrupted exception
            }
        }
        // Wait for the clock to shut down
        if (this.clockThread != null) {
            try {
                this.clockThread.join();
            } catch (InterruptedException e) {
                // TODO handle interrupted exception
            }
        }
    }

    public Thread start() {
        Thread t = new Thread(this, getName());
        t.start();
        return t;
    }

}
