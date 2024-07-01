package io.ciera.runtime.summit.application;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import io.ciera.runtime.instanceloading.ChangeLog;
import io.ciera.runtime.instanceloading.IChangeLog;
import io.ciera.runtime.instanceloading.IModelDelta;
import io.ciera.runtime.summit.application.tasks.HaltExecutionTask;
import io.ciera.runtime.summit.application.tasks.TimerExpiredTask;
import io.ciera.runtime.summit.exceptions.StateMachineException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.Event;
import io.ciera.runtime.summit.statemachine.EventHandle;
import io.ciera.runtime.summit.statemachine.EventSet;
import io.ciera.runtime.summit.statemachine.IEvent;
import io.ciera.runtime.summit.time.Timer;
import io.ciera.runtime.summit.time.TimerHandle;
import io.ciera.runtime.summit.time.TimerSet;

public class ApplicationExecutor implements Runnable, IRunContext {

    private static final int TICK_LEN = 1; // tick length in milliseconds
    
    private String name;

    private IExceptionHandler handler;
    private Queue<IApplicationTask> pendingEvents;
    private Queue<IApplicationTask> tasks;
    private boolean running;
    
    private IChangeLog changeLog;

    private Queue<Timer> activeTimers;
    private Map<EventHandle, IEvent> activeEvents;

    private Instant epoch;
    private long systemTime;  // current system time in microseconds since the configured epoch
    private long lastSystemTime;
    private boolean simulatedTime;
    
    private ILogger logger;

    private String[] args;

    public ApplicationExecutor(String name) {
        this(name, new String[0]);
    }

    public ApplicationExecutor(String name, String[] args) {
        this(name, args, new DefaultLogger());
    }

    public ApplicationExecutor(String name, String[] args, ILogger logger) {
        this.name = name;
        handler = new DefaultExceptionHandler(this);
        pendingEvents = new PriorityQueue<>();
        tasks = new PriorityQueue<>();
        activeTimers = new PriorityQueue<>();
        activeEvents = new HashMap<>();
        running = false;
        changeLog = null;
        epoch = Instant.EPOCH;
        lastSystemTime = System.currentTimeMillis();
        systemTime = lastSystemTime * 1000L;
        simulatedTime = false;
        this.logger = logger;
        this.args = args;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute(IApplicationTask task) {
        if (task != null) {
            if ( task.getPriority() < 0x10 ) pendingEvents.add(task);
            else tasks.add(task);
        }
    }

    @Override
    public void run() {
        running = true;
        while (running) {
    	    tick();
        	if (!tasks.isEmpty()) {
        	    performTransaction(tasks.poll());
        	}
        	// if the queue is empty, go to sleep
        	else {
                try {
                    Thread.sleep(TICK_LEN);
                } catch (InterruptedException e) {/* do nothing */}
        	}
        }
    }
    
    @Override
    public IChangeLog heartbeat() {
    	tick();
        if (!tasks.isEmpty()) {
            return performTransaction(tasks.poll());
        }
        else return new ChangeLog();
    }

    @Override
    public IChangeLog performTransaction(IApplicationTask task) {
        changeLog = new ChangeLog();
        // execute a single task
        if (task instanceof HaltExecutionTask) {
            running = false;
        }
        else {
            try {
                task.run();
            } catch (XtumlException e) {
              handler.handle(e);
            }
        }
        // handle all generated events
        while (!pendingEvents.isEmpty()) {
            IApplicationTask pendingEvent = pendingEvents.poll();
            try {
                pendingEvent.run();
            } catch (XtumlException e) {
                handler.handle(e);
            }
        }
        // end transaction
        return changeLog;
    }

    private void tick() {
        if (!activeTimers.isEmpty() && (simulatedTime || time() >= activeTimers.peek().getWakeUpTime())) {
            Timer t = activeTimers.poll();
            // set the clock to the timer wake up time (simulated time only)
            if (simulatedTime)
                setTime(t.getWakeUpTime());
            // generate the event
            final IEvent event = getEvent(t.getEventToGenerate());
            if ( null != event ) {
                execute(new TimerExpiredTask() {
                    @Override
                    public void run() throws XtumlException {
                        event.getTarget().accept(event);
                    }
                });
                // reset recurring timer
                if (t.isRecurring()) {
                	long oldTime = t.getWakeUpTime();
                    t.reset(time());
                	activeTimers.add(t);
                	addChange(new Timer.TimerAttributeChangedDelta(t, "wakeUpTime", oldTime, t.getWakeUpTime()));
                }
                // deregister non-recurring event
                else {
                    deregisterEvent(t.getEventToGenerate());
                }
            }
            else {
            	handler.handle(new StateMachineException("Could not acquire event instance"));
            }
        }
    }

    @Override
    public IExceptionHandler getExceptionHandler() {
        return handler;
    }

    @Override
    public void setExceptionHandler(IExceptionHandler h) {
        if (null != h)
            handler = h;
    }

    @Override
    public String[] args() {
        return args;
    }

    @Override
    public TimerHandle addTimer(Timer timer) {
        activeTimers.add(timer);
        addChange(new Timer.TimerCreatedDelta(timer));
        return timer.getId();
    }

    @Override
    public boolean cancelTimer(TimerHandle t) {
    	for ( Timer timer : activeTimers ) {
    		if ( timer.getId().equals(t) ) {
    			boolean success = activeTimers.remove(timer);
    			if (success) {
    			    deregisterEvent(timer.getEventToGenerate());
                    addChange(new Timer.TimerDeletedDelta(timer));
    			}
    			return success;
    		}
    	}
        return false;
    }

    // Get system time in microseconds
    @Override
    public long time() {
        if (!simulatedTime) {
            long now = System.currentTimeMillis();
            systemTime += (now - lastSystemTime) * 1000L;
            lastSystemTime = now;
        }
        return systemTime;
    }

    @Override
    public void setTime(long time) {
        systemTime = time;
    }

    @Override
    public void setEpoch(Instant newEpoch) {
        systemTime -= epoch.until(newEpoch, ChronoUnit.MICROS);  // adjust system time as the epoch changes
        epoch = newEpoch;
    }

    @Override
    public Instant getEpoch() {
        return epoch;
    }

    @Override
    public void enableSimulatedTime(boolean enable) {
        simulatedTime = enable;
    }

	@Override
	public TimerSet getActiveTimers() {
		return new TimerSet(activeTimers);
	}

	@Override
	public EventSet getActiveEvents() {
		return new EventSet(activeEvents.values());
	}

	@Override
	public void registerEvent(IEvent event) {
		activeEvents.put(event.getEventHandle(), event);
        addChange(new Event.EventCreatedDelta(event));
	}

	@Override
	public void deregisterEvent(EventHandle e) {
		IEvent event = getEvent(e);
		activeEvents.remove(e);
        addChange(new Event.EventDeletedDelta(event));
	}

	@Override
	public IEvent getEvent(EventHandle e) {
		return activeEvents.get(e);
	}

	@Override
	public Timer getTimer(TimerHandle t) {
    	for ( Timer timer : activeTimers ) {
    		if ( timer.getId().equals(t) ) return timer;
    	}
		return null;
	}

	@Override
	public void addChange(IModelDelta delta) {
		if (null != changeLog) {
		    changeLog.addChange(delta);
		}
	}
	
	@Override
	public IChangeLog getChangeLog() {
		return (null != changeLog) ? changeLog : new ChangeLog();
	}

    @Override
    public ILogger getLog() {
        return logger;
    }

    @Override
    public void start() {
        new Thread(this, getName()).start();
    }

}
