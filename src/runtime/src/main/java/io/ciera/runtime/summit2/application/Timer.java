package io.ciera.runtime.summit2.application;

import io.ciera.runtime.summit2.types.EventHandle;
import io.ciera.runtime.summit2.types.TimerHandle;
import io.ciera.runtime.summit2.types.UniqueId;

public class Timer implements Comparable<Timer> {

    private TimerHandle timerHandle;
    private EventHandle eventHandle;
    private UniqueId targetHandle;
    private long expiration;
    private long period;
    private boolean recurring;

    public Timer(EventHandle eventHandle, UniqueId targetHandle, long expiration, long period) {
        this(UniqueId.random().castTo(TimerHandle.class), eventHandle, targetHandle, expiration, period);

    }

    public Timer(TimerHandle timerHandle, EventHandle eventHandle, UniqueId targetHandle, long expiration,
            long period) {
        this.timerHandle = timerHandle;
        this.eventHandle = eventHandle;
        this.targetHandle = targetHandle;
        this.expiration = expiration;
        this.period = period;
        this.recurring = period > 0;
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

    @Override
    public int compareTo(Timer o) {
        return Long.compare(expiration, o.getExpiration()) * -1;
    }

}
