package io.ciera.runtime.application;

import io.ciera.runtime.application.task.TimerExpiration;
import io.ciera.runtime.types.Date;
import io.ciera.runtime.types.Duration;
import io.ciera.runtime.types.TimeStamp;
import io.ciera.runtime.types.UniqueId;

public class Timer implements Comparable<Timer> {

    private final UniqueId timerHandle;
    private final ExecutionContext context;
    private final Event event;
    private final EventTarget target;

    private final long period;
    protected long expiration;

    private boolean scheduled;
    private boolean expired;

    public Timer(ExecutionContext context, Event event, EventTarget target) {
        this(UniqueId.random(), context, event, target, Duration.ZERO);
    }

    public Timer(ExecutionContext context, Event event, EventTarget target, Duration period) {
        this(UniqueId.random(), context, event, target, period);
    }

    public Timer(UniqueId timerHandle, ExecutionContext context, Event event, EventTarget target, Duration period) {
        this.timerHandle = timerHandle;
        this.context = context;
        this.event = event;
        this.target = target;
        this.period = period.getValue();
        this.expiration = 0;
        this.scheduled = false;
        this.expired = false;
    }

    protected boolean schedule(long delay) {
        context.getApplication().getLogger().trace("TMR: Scheduling timer: %s: %s -> %s at %s", this, event, target,
                new Date(context.getClock().getTime() + delay));
        synchronized (context) {
            scheduled = context.getClock().scheduleTimer(context, this, event, target, delay);
            context.notify();
        }
        return scheduled;
    }

    // trigger the scheduled event and reschedule recurring timers
    protected void fire() {
        context.getApplication().getLogger().trace("TMR: Firing timer: %s", this);
        expired = true;
        context.addTask(new TimerExpiration(context, event, target));
        if (period > 0l) {
            schedule(period);
        } else {
            scheduled = false;
        }
    }

    // attempt to cancel the timer
    // return true if successful
    public boolean cancel() {
        context.getApplication().getLogger().trace("TMR: Cancelling timer: %s", this);
        scheduled = false;
        expired = false;
        if (context.getClock().cancelTimer(context, this)) {
            return true;
        } else {
            return false;
        }
    }

    // a timer is "scheduled" from the point at which it is first scheduled
    // until it either expires and is not rescheduled (non-recurring) or is
    // cancelled
    public boolean isScheduled() {
        return scheduled;
    }

    // a timer is expired from the point where it fires the first time until it
    // is cancelled. after the first expiration, a recurring timer is both expired
    // and cancelled.
    public boolean isExpired() {
        return expired;
    }

    public TimeStamp remainingTime() {
        return new TimeStamp(expiration - context.getClock().getTime());
    }

    public TimeStamp getScheduledExpirationTime() {
        if (scheduled) {
            return new TimeStamp(expiration);
        } else {
            throw new IllegalStateException("Timer has not been scheduled: " + this);
        }
    }

    // return the last expiration time (scheduled expiration, not real time at
    // expiration)
    public TimeStamp getLastExpirationTime() {
        if (expired) {
            return new TimeStamp(scheduled ? expiration - period : expiration);
        } else {
            throw new IllegalStateException("Timer is not expired: " + this);
        }
    }

    public Duration getPeriod() {
        return new Duration(period);
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

    @Override
    public int compareTo(Timer o) {
        return Long.compare(expiration, o.expiration);
    }

    @Override
    public String toString() {
        return String.format("Timer[%.8s]", timerHandle);
    }

}
