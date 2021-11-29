package io.ciera.runtime.application;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import io.ciera.runtime.application.task.TimerExpiration;

public abstract class SystemClock {

    protected final Map<ExecutionContext, Queue<Timer>> activeTimers;

    private Instant epoch;

    public SystemClock() {
        this.activeTimers = new HashMap<>();
        this.epoch = Instant.EPOCH;
    }

    public abstract long getTime();

    public abstract void setTime(long time);

    public Instant getEpoch() {
        return epoch;
    }

    public void setEpoch(Instant epoch) {
        this.epoch = epoch;
    }

    protected void checkTimers(ExecutionContext context) {
        Queue<Timer> contextTimers = activeTimers.get(context);
        while (contextTimers != null && !contextTimers.isEmpty() && contextTimers.peek().isExpired()) {
            Timer timer = contextTimers.poll();
            context.addTask(new TimerExpiration(context, timer, timer.getEvent(), timer.getTarget()));
        }
    }

    protected boolean hasActiveTimers(ExecutionContext context) {
        Queue<Timer> contextTimers = activeTimers.get(context);
        return contextTimers != null && !contextTimers.isEmpty();
    }

    protected abstract void waitForNextTimer(ExecutionContext context) throws InterruptedException;

    protected void registerTimer(ExecutionContext context, Timer timer, Event event, EventTarget target) {
        Queue<Timer> contextTimers = activeTimers.get(context);
        if (contextTimers == null) {
            contextTimers = new PriorityQueue<>();
            activeTimers.put(context, contextTimers);
        }
        contextTimers.add(timer);
    }

    protected boolean cancelTimer(ExecutionContext context, Timer timer) {
        Queue<Timer> contextTimers = activeTimers.get(context);
        return contextTimers != null && contextTimers.remove(timer);
    }

}
