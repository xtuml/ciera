package io.ciera.runtime.summit.application;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import io.ciera.runtime.summit.application.tasks.HaltExecutionTask;
import io.ciera.runtime.summit.application.tasks.TimerExpiredTask;
import io.ciera.runtime.summit.exceptions.StateMachineException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.EventHandle;
import io.ciera.runtime.summit.statemachine.EventSet;
import io.ciera.runtime.summit.statemachine.IEvent;
import io.ciera.runtime.summit.time.Timer;
import io.ciera.runtime.summit.time.TimerHandle;
import io.ciera.runtime.summit.time.TimerSet;

public class ApplicationExecutor extends Thread implements IRunContext {

    private static final int TICK_LEN = 1; // tick length in milliseconds
	private static long startTime = 0;     // system start time

    private IExceptionHandler handler;
    private Queue<IApplicationTask> tasks;
    private boolean running;

    private Queue<Timer> activeTimers;
    private Map<EventHandle, IEvent> activeEvents;

    private long currentTime;
    private boolean simulatedTime;

    private String[] args;

    public ApplicationExecutor(String name) {
        this(name, new String[0]);
    }

    public ApplicationExecutor(String name, String[] args) {
        super(name);
        handler = new DefaultExceptionHandler();
        tasks = new PriorityQueue<>();
        activeTimers = new PriorityQueue<>();
        activeEvents = new HashMap<>();
        running = false;
        currentTime = 0;
        simulatedTime = true;
        this.args = args;
        startTime = time();
    }

    @Override
    public void execute(IApplicationTask task) {
        tasks.add(task);
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            // evaluate timers
            tick();
            // handle waiting tasks
            handleTasks();
            // sleep for a tick
            try {
                Thread.sleep(TICK_LEN);
            } catch (InterruptedException e) {
                /* do nothing */ }
        }
    }

    private void handleTasks() {
        // execute waiting tasks
        while (!tasks.isEmpty()) {
            IApplicationTask task = tasks.poll();
            if (task instanceof HaltExecutionTask) {
                running = false;
            } else {
                try {
                    task.run();
                } catch (XtumlException e) {
                    handler.handle(e);
                }
            }
        }
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
                    t.reset(Math.toIntExact(time()));
                    addTimer(t);
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
        return timer.getId();
    }

    @Override
    public boolean cancelTimer(TimerHandle t) {
    	for ( Timer timer : activeTimers ) {
    		if ( timer.getId().equals(t) ) return activeTimers.remove(timer);
    	}
        return false;
    }

    @Override
    public long time() {
        if (simulatedTime)
            return currentTime;
        else
            return (System.currentTimeMillis() * 1000) - startTime;
    }

    private void setTime(long time) {
        currentTime = time;
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
	public EventHandle registerEvent(IEvent event) {
		EventHandle e = EventHandle.random();
		activeEvents.put(e, event);
		return e;
	}

	@Override
	public void deregisterEvent(EventHandle e) {
		activeEvents.remove(e);
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

}
