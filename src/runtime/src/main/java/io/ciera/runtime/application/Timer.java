package io.ciera.runtime.application;

import io.ciera.runtime.types.UniqueId;

public class Timer implements Comparable<Timer>, Named {

    private final UniqueId timerHandle;
    private final ExecutionContext context;
    private final Event event;
    private final EventTarget target;
    private final long period;
    private final boolean recurring;
    private long expiration;

    public Timer(ExecutionContext context, Event event, EventTarget target, long expiration) {
        this(UniqueId.random(), context, event, target, expiration);

    }

    public Timer(UniqueId timerHandle, ExecutionContext context, Event event, EventTarget target, long expiration) {
        this.timerHandle = timerHandle;
        this.context = context;
        this.event = event;
        this.target = target;
        this.expiration = expiration;
        this.period = 0;
        this.recurring = false;
    }

    public Timer(ExecutionContext context, Event event, EventTarget target, long expiration, long period) {
        this(UniqueId.random(), context, event, target, expiration, period);

    }

    public Timer(UniqueId timerHandle, ExecutionContext context, Event event, EventTarget target, long expiration,
            long period) {
        this.timerHandle = timerHandle;
        this.context = context;
        this.event = event;
        this.target = target;
        this.expiration = expiration;
        this.period = period;
        this.recurring = true;
    }

    public UniqueId getTimerHandle() {
        return timerHandle;
    }

    public Event getEvent() {
        return event;
    }

    public EventTarget getTarget() {
        return target;
    }

    public long getExpiration() {
        return expiration;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public boolean isExpired() {
        return expiration <= context.getClock().getTime();
    }

    public long remainingTime() {
        return expiration - context.getClock().getTime();
    }

    public boolean reset() {
        if (recurring) {
            boolean cancelled = this.cancel();
            expiration += period;
            context.scheduleEvent(event, target, this);
            return cancelled;
        } else {
            return false;
        }
    }

    public boolean reset(long expiration) {
        boolean cancelled = this.cancel();
        this.expiration = expiration;
        context.scheduleEvent(event, target, this);
        return cancelled;
    }

    public boolean addTime(long duration) {
        boolean cancelled = this.cancel();
        expiration += duration;
        context.scheduleEvent(event, target, this);
        return cancelled;
    }

    public boolean cancel() {
        return context.getClock().cancelTimer(context, this);
    }

    @Override
    public int compareTo(Timer o) {
        return Long.compare(expiration, o.getExpiration());
    }

    @Override
    public String getName() {
        return String.format("Timer[%s]", timerHandle.toString().substring(0, 8));
    }

}
