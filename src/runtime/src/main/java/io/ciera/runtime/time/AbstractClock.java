package io.ciera.runtime.time;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.time.SystemClock;
import io.ciera.runtime.api.time.Timer;
import io.ciera.runtime.api.types.Duration;

public abstract class AbstractClock implements SystemClock {

    protected final Map<ExecutionContext, Queue<Timer>> scheduledTimersMap;
    private final Set<Timer> allScheduledTimers;

    private Instant epoch;

    public AbstractClock() {
        this.scheduledTimersMap = new ConcurrentHashMap<>();
        this.allScheduledTimers = Collections.synchronizedSet(new HashSet<>());
        this.epoch = Instant.EPOCH;
    }

    @Override
    public abstract long getTime();

    @Override
    public abstract void setTime(long time);

    @Override
    public Instant getEpoch() {
        return epoch;
    }

    @Override
    public void setEpoch(Instant epoch) {
        this.epoch = epoch;
    }

    @Override
    public void checkTimers(ExecutionContext context) {
        Queue<Timer> timers = scheduledTimersMap.get(context);
        while (timers != null && !timers.isEmpty() && (getTime() >= timers.peek().getExpiration())) {
            Timer timer = timers.poll();
            // long clockError = getTime() - timer.expiration; TODO report if greater than
            // some threshhold
            allScheduledTimers.remove(timer);
            timer.fire();
        }
    }

    @Override
    public Collection<Timer> getTimers(ExecutionContext context) {
        return Optional.ofNullable(scheduledTimersMap.get(context)).orElse(new PriorityQueue<>());
    }

    @Override
    public void registerTimers(Collection<Timer> timers) {
        timers.stream().forEach(t -> this.registerTimer(t.getContext(), t));
    }

    @Override
    public boolean hasScheduledTimers(ExecutionContext context) {
        Queue<Timer> timers = scheduledTimersMap.get(context);
        return timers != null && !timers.isEmpty();
    }

    @Override
    public boolean scheduleTimer(ExecutionContext context, Timer timer, Event event, EventTarget target, long delay) {
        if (delay < 0 && System.getProperty("io.ciera.runtime.dropNegativeDelays") != null) {
            context.getApplication().getLogger().trace("Dropping timer with negative delay: %s, %s", new Duration(delay), timer);
            timer.cancel();
            return false;
        } else {
            timer.setExpiration(timer.isExpired() ? timer.getExpiration() + delay : getTime() + delay);
            return registerTimer(context, timer);
        }
    }

    private boolean registerTimer(ExecutionContext context, Timer timer) {
        if (allScheduledTimers.add(timer)) {
            Queue<Timer> timers = scheduledTimersMap.get(context);
            if (timers == null) {
                timers = new PriorityQueue<>();
                scheduledTimersMap.put(context, timers);
            }
            timers.add(timer);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean cancelTimer(ExecutionContext context, Timer timer) {
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
