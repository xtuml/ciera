package io.ciera.runtime.summit2.application;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.PriorityQueue;
import java.util.Queue;

import io.ciera.runtime.summit2.application.task.TimerExpiration;

public abstract class SystemClock {

    private ExecutionContext context;
    private Instant epoch;
    private Queue<ActiveTimer> activeTimers;

    public SystemClock(ExecutionContext context) {
        this.context = context;
        this.epoch = Instant.EPOCH;
        this.activeTimers = new PriorityQueue<>();
    }
    
    protected Queue<ActiveTimer> getActiveTimers() {
        return activeTimers;
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

    public void registerTimer(Timer timer, Event event, EventTarget target) {
        activeTimers.add(new ActiveTimer(timer, event, target));
    }
    
    protected void expireTimer(ActiveTimer activeTimer) {
        context.addTask(new TimerExpiration(context, activeTimer.getTimer(), activeTimer.getEvent(), activeTimer.getTarget()));
    }
    
    protected static class ActiveTimer implements Comparable<ActiveTimer>{

        private Timer timer;
        private Event event;
        private EventTarget target;

        public ActiveTimer(Timer timer, Event event, EventTarget target) {
            this.timer = timer;
            this.event = event;
            this.target = target;
        }

        public Timer getTimer() {
            return timer;
        }

        public Event getEvent() {
            return event;
        }

        public EventTarget getTarget() {
            return target;
        }
        
        public boolean isExpired(long currentTime) {
            return timer.getExpiration().getValue() < currentTime;
        }

        @Override
        public int compareTo(ActiveTimer o) {
            return timer.compareTo(o.getTimer());
        }

    }
    
}
