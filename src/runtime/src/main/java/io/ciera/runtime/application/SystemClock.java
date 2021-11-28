package io.ciera.runtime.application;

import java.time.Instant;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

import io.ciera.runtime.application.task.TimerExpiration;
import io.ciera.runtime.types.TimerHandle;

public abstract class SystemClock {

    private final ExecutionContext context;
    private final Queue<ActiveTimer> activeTimers;

    private Instant epoch;

    public SystemClock(ExecutionContext context) {
        this.context = context;
        this.activeTimers = new PriorityQueue<>();
        this.epoch = Instant.EPOCH;
    }

    protected synchronized Queue<ActiveTimer> getActiveTimers() {
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

    public synchronized void registerTimer(Timer timer, Event event, EventTarget target) {
        activeTimers.add(new ActiveTimer(timer, event, target));
    }

    protected void expireTimer(ActiveTimer activeTimer) {
        context.addTask(
                new TimerExpiration(context, activeTimer.getTimer(), activeTimer.getEvent(), activeTimer.getTarget()));
    }

    public synchronized boolean cancelTimer(TimerHandle timerHandle) {
        for (ActiveTimer activeTimer : Collections.unmodifiableCollection(activeTimers)) {
            if (activeTimer.getTimer().getTimerHandle().equals(timerHandle)) {
                activeTimers.remove(activeTimer);
                getContext().getInstancePopulation().removeTimer(timerHandle);
                getContext().getInstancePopulation().removeEvent(activeTimer.getEvent().getEventHandle());
                return true;
            }
        }
        return false;
    }

    protected static class ActiveTimer implements Comparable<ActiveTimer> {

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
            return timer.getExpiration() < currentTime;
        }

        @Override
        public int compareTo(ActiveTimer o) {
            return timer.compareTo(o.getTimer());
        }

    }

}
