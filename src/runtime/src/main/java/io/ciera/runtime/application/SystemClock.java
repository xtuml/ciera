package io.ciera.runtime.application;

import java.time.Instant;
import java.util.PriorityQueue;
import java.util.Queue;

import io.ciera.runtime.application.task.TimerExpiration;

public abstract class SystemClock {

    private final ExecutionContext context;
    protected final Queue<Timer> activeTimers;

    private Instant epoch;

    public SystemClock(ExecutionContext context) {
        this.context = context;
        this.activeTimers = new PriorityQueue<>();
        this.epoch = Instant.EPOCH;
    }

    public ExecutionContext getContext() {
        return context;
    }

    public abstract long getTime();

    public abstract void setTime(long time);

    public Instant getEpoch() {
        return epoch;
    }

    public void setEpoch(Instant epoch) {
        this.epoch = epoch;
    }

    protected void checkTimers() {
        while (!activeTimers.isEmpty() && activeTimers.peek().isExpired(getTime())) {
            Timer timer = activeTimers.poll();
            context.addTask(new TimerExpiration(context, timer, timer.getEvent(), timer.getTarget()));
        }
    }

    protected boolean hasActiveTimers() {
        return !activeTimers.isEmpty();
    }

    protected abstract void waitForNextTimer() throws InterruptedException;

    public void registerTimer(Timer timer, Event event, EventTarget target) {
        activeTimers.add(timer);
    }

    public boolean cancelTimer(Timer timer) {
        boolean cancelled = activeTimers.remove(timer);
        if (cancelled) {
            getContext().getInstancePopulation().removeTimer(timer);
            getContext().getInstancePopulation().removeEvent(timer.getEvent());
        }
        return cancelled;
    }

}
