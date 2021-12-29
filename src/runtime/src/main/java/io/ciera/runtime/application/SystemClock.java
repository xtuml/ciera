package io.ciera.runtime.application;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.ciera.runtime.exceptions.TimerException;
import io.ciera.runtime.types.Duration;

public abstract class SystemClock {

    protected final Map<ExecutionContext, Queue<Timer>> scheduledTimersMap;
    private final Set<Timer> allScheduledTimers;

    private Instant epoch;

    public SystemClock() {
        this.scheduledTimersMap = new ConcurrentHashMap<>();
        this.allScheduledTimers = Collections.synchronizedSet(new HashSet<>());
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
        Queue<Timer> timers = scheduledTimersMap.get(context);
        while (timers != null && !timers.isEmpty() && (getTime() >= timers.peek().expiration)) {
            Timer timer = timers.poll();
            // long clockError = getTime() - timer.expiration; TODO report if greater than
            // some threshhold
            allScheduledTimers.remove(timer);
            timer.fire();
        }
    }

    protected boolean hasScheduledTimers(ExecutionContext context) {
        Queue<Timer> timers = scheduledTimersMap.get(context);
        return timers != null && !timers.isEmpty();
    }

    protected abstract void waitForNextTimer(ExecutionContext context) throws InterruptedException;

    protected boolean scheduleTimer(ExecutionContext context, Timer timer, Event event, EventTarget target,
            long delay) {
        if (delay >= 0) {
            if (allScheduledTimers.add(timer)) {
                Queue<Timer> timers = scheduledTimersMap.get(context);
                if (timers == null) {
                    timers = new PriorityQueue<>();
                    scheduledTimersMap.put(context, timers);
                }
                timer.expiration = getTime() + delay;
                timers.add(timer);
                return true;
            } else {
                return false;
            }
        } else {
            throw new TimerException(
                    String.format("Attempt to schedule timer %s in the past: %s", timer, new Duration(-delay)));
        }
    }

    protected boolean cancelTimer(ExecutionContext context, Timer timer) {
        if (allScheduledTimers.remove(timer)) {
            Queue<Timer> timers = scheduledTimersMap.get(context);
            if (timers != null) {
                timers.remove(timer);
            }
            return true;
        } else {
            return false;
        }
    }

}
