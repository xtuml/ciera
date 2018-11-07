package io.ciera.runtime.summit.application;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import io.ciera.runtime.summit.application.tasks.HaltExecutionTask;
import io.ciera.runtime.summit.application.tasks.PoppedTimerTask;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.IEvent;
import io.ciera.runtime.summit.time.Timer;

public class ApplicationExecutor extends Thread implements IRunContext {

    private IExceptionHandler handler;
    private BlockingQueue<IApplicationTask> tasks;
    private Queue<Timer> activeTimers;
    private boolean running;

    private String[] args;

    public ApplicationExecutor(String name) {
        this(name, new String[0]);
    }

    public ApplicationExecutor(String name, String[] args) {
        super(name);
        handler = new DefaultExceptionHandler();
        tasks = new PriorityBlockingQueue<>();
        activeTimers = new PriorityQueue<>();
        running = false;
        this.args = args;
    }

    @Override
    public void execute(IApplicationTask task) {
        tasks.add(task);
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            // try to execute a task
            try {
                IApplicationTask task = tasks.poll(1, TimeUnit.MILLISECONDS);
                if ( null != task ) {
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
            } catch (InterruptedException e) { /* do nothing */ }
            // check for expired timers
            Timer t = activeTimers.peek();
            while ( t != null && t.getWakeUpTime() < System.currentTimeMillis() ) {
            	t = activeTimers.poll();
                final IEvent event = t.getEventToGenerate();
                execute(new PoppedTimerTask() {
                    @Override
                    public void run() throws XtumlException {
                        event.getTarget().accept(event);
                    }
                });
                if ( t.isRecurring() ) {
                	t.reset();
                	activeTimers.add(t);
                }
                t = activeTimers.peek();
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

}
