package io.ciera.runtime.application;

import io.ciera.runtime.types.UniqueId;

public class Timer implements Comparable<Timer>, Named {

    private UniqueId timerHandle;
    private Event event;
    private EventTarget target;
    private long expiration;
    private long period;
    private boolean recurring;

    public Timer(Event event, EventTarget target, long expiration, long period) {
        this(UniqueId.random(), event, target, expiration, period);

    }

    public Timer(UniqueId timerHandle, Event event, EventTarget target, long expiration, long period) {
        this.timerHandle = timerHandle;
        this.event = event;
        this.target = target;
        this.expiration = expiration;
        this.period = period;
        this.recurring = period > 0;
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

    public void reset() {
        if (recurring) {
            expiration += period;
        }
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public boolean isExpired(long currentTime) {
        return expiration <= currentTime;
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
