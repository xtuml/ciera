package io.ciera.runtime.summit.application;

import java.util.PriorityQueue;
import java.util.Queue;

import io.ciera.runtime.summit.application.tasks.HaltExecutionTask;
import io.ciera.runtime.summit.application.tasks.TimerExpiredTask;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.IEvent;
import io.ciera.runtime.summit.time.Timer;

public class ApplicationExecutor extends Thread implements IRunContext {

    private static final int TICK_LEN = 1; // tick length in milliseconds

    private IExceptionHandler handler;
    private Queue<IApplicationTask> tasks;
    private Queue<Timer> activeTimers;
    private boolean running;

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
        running = false;
        currentTime = 0;
        simulatedTime = true;
        this.args = args;
        tick();
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
        if (!activeTimers.isEmpty() && (simulatedTime || (time() * 1000) >= activeTimers.peek().getWakeUpTime())) {
            Timer t = activeTimers.poll();
            // set the clock to the timer wake up time (simulated time only)
            if (simulatedTime)
                setTime(t.getWakeUpTime());
            // generate the event
            final IEvent event = t.getEventToGenerate();
            execute(new TimerExpiredTask() {
                @Override
                public void run() throws XtumlException {
                    event.getTarget().accept(event);
                }
            });
            // reset recurring timer
            if (t.isRecurring()) {
                t.reset(time() * 1000);
                activeTimers.add(t);
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
    public void addTimer(Timer timer) {
        activeTimers.add(timer);
    }

    @Override
    public boolean cancelTimer(Timer timer) {
        return activeTimers.remove(timer);
    }

    @Override
    public long time() {
        if (simulatedTime)
            return currentTime;
        else
            return System.currentTimeMillis();
    }

    private void setTime(long time) {
        currentTime = time;
    }

}
