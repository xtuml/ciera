package io.ciera.runtime.summit2.application;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import io.ciera.runtime.summit2.application.task.GeneratedEvent;
import io.ciera.runtime.summit2.application.task.GeneratedEventToSelf;
import io.ciera.runtime.summit2.types.Duration;
import io.ciera.runtime.summit2.types.TimeStamp;

public class ExecutionContext implements Runnable {

    private int id;
    private Application application;
    private ExecutionMode executionMode;
    private ModelIntegrityMode modelIntegrityMode;

    private SystemClock clock;
    private Thread clockThread;
    private int sequenceNumber;
    private BlockingQueue<Task> tasks;

    public ExecutionContext(int id, Application application) {
        this(id, application, ExecutionMode.INTERLEAVED, ModelIntegrityMode.STRICT, 1000000l, false);

    }

    public ExecutionContext(int id, Application application, ExecutionMode executionMode,
            ModelIntegrityMode modelIntegrityMode, long tickDuration, boolean enableSimulatedTime) {
        this.id = id;
        this.executionMode = executionMode;
        this.modelIntegrityMode = modelIntegrityMode;
        this.application = application;
        this.sequenceNumber = 1;
        this.tasks = new PriorityBlockingQueue<>();
        
        if (!enableSimulatedTime) {
            this.clock = new WallClock(this, tickDuration);
            this.clockThread = new Thread((WallClock)this.clock, "Clock: " + getName());
            this.clockThread.start();
        } else {
            this.clockThread = null;
            // TODO create simulated clock
        }
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
    
    public void generateEvent(Event event, EventTarget target) {
        addTask(new GeneratedEvent(this, event, target));
    }
    
    public void generateEventToSelf(Event event, EventTarget target) {
        addTask(new GeneratedEventToSelf(this, event, target));
    }
    
    public void scheduleEvent(Event event, EventTarget target, TimeStamp expiration, Duration period) {
        clock.registerTimer(new Timer(event.getEventHandle(), target.getTargetHandle(), expiration, period), event, target);
    }

    public void scheduleEvent(Event event, EventTarget target, TimeStamp expiration) {
        scheduleEvent(event, target, expiration, null);
    }

    public void scheduleEvent(Event event, EventTarget target, Duration delay, Duration period) {
        TimeStamp expiration = TimeStamp.now(clock).add(delay).castTo(TimeStamp.class);
        scheduleEvent(event, target, expiration, period);
    }

    public void scheduleEvent(Event event, EventTarget target, Duration delay) {
        scheduleEvent(event, target, delay, null);
    }
    
    public void addTask(Task newTask) {
        tasks.offer(newTask);
    }

    @Override
    public void run() {
        while (application.isRunning()) {
            try {
                Task task = tasks.take();
                try {
                    // TODO initialize transaction
                    task.run();
                    // TODO complete transaction
                } catch (RuntimeException e) {
                    // TODO invoke exception handler
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
