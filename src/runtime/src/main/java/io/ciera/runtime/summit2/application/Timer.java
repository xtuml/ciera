package io.ciera.runtime.summit2.application;

import io.ciera.runtime.summit2.types.Duration;
import io.ciera.runtime.summit2.types.EventHandle;
import io.ciera.runtime.summit2.types.TimeStamp;
import io.ciera.runtime.summit2.types.TimerHandle;
import io.ciera.runtime.summit2.types.UniqueId;

public class Timer implements Comparable<Timer> {

    private TimerHandle timerHandle;
    private EventHandle eventHandle;
    private UniqueId targetHandle;
    private TimeStamp expiration;
    private Duration period;
    private boolean recurring;

    public Timer(EventHandle eventHandle, UniqueId targetHandle, TimeStamp expiration, Duration period) {
        this(UniqueId.random().castTo(TimerHandle.class), eventHandle, targetHandle, expiration, period);

    }

    public Timer(TimerHandle timerHandle, EventHandle eventHandle, UniqueId targetHandle, TimeStamp expiration,
            Duration period) {
        this.timerHandle = timerHandle;
        this.eventHandle = eventHandle;
        this.targetHandle = targetHandle;
        this.expiration = expiration;
        this.period = period;
        this.recurring = period != null;
    }

    public TimerHandle getTimerHandle() {
        return timerHandle;
    }

    public EventHandle getEventHandle() {
        return eventHandle;
    }

    public UniqueId getTargetHandle() {
        return targetHandle;
    }

    public TimeStamp getExpiration() {
        return expiration;
    }

    public Duration getPeriod() {
        return period;
    }

    public boolean isRecurring() {
        return recurring;
    }

    @Override
    public int compareTo(Timer o) {
        return expiration.compareTo(o.getExpiration()) * -1;
    }

}
