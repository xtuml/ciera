package io.ciera.runtime.time;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.application.Logger;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.time.Timer;
import io.ciera.runtime.api.types.Date;
import io.ciera.runtime.api.types.Duration;
import io.ciera.runtime.api.types.TimeStamp;
import io.ciera.runtime.api.types.UniqueId;
import io.ciera.runtime.application.BaseApplication;
import io.ciera.runtime.application.task.TimerExpiration;

public class EventTimer implements Timer {

    private static final long serialVersionUID = 1L;

    private final Class<? extends Domain> domainClass;
    private final UniqueId timerHandle;
    private final Event event;
    private final UniqueId targetId;
    private final String contextId;

    private transient EventTarget target;
    private transient ExecutionContext context;

    private final long period;
    private long expiration;

    private boolean scheduled;
    private boolean expired;

    public EventTimer(ExecutionContext context, Event event, EventTarget target) {
        this(UniqueId.random(), context, event, target, Duration.ZERO);
    }

    public EventTimer(ExecutionContext context, Event event, EventTarget target, Duration period) {
        this(UniqueId.random(), context, event, target, period);
    }

    public EventTimer(UniqueId timerHandle, ExecutionContext context, Event event, EventTarget target,
            Duration period) {
        this.domainClass = target.getDomain().getClass();
        this.timerHandle = timerHandle;
        this.context = context;
        this.contextId = context.getName();
        this.event = event;
        this.target = target;
        this.targetId = target.getTargetId();
        this.period = period.getValue();
        this.expiration = 0;
        this.scheduled = false;
        this.expired = false;
    }

    public boolean schedule(long delay) {
        getLogger().trace("TMR: Scheduling timer: %s: %s -> %s at %s", this, event, getTarget(),
                new Date(getContext().getClock().getTime() + delay));
        synchronized (getContext()) {
            if (delay < 0 && System.getProperty("io.ciera.runtime.dropNegativeDelays") != null) {
                getLogger().trace("Dropping timer with negative delay: %s, %s", new Duration(delay), this);
                cancel();
            } else {
                expiration = expired ? expiration + delay : getContext().getClock().getTime() + delay;
                scheduled = getContext().getClock().registerTimer(this);
                getContext().notify();
            }
        }
        return scheduled;
    }

    // trigger the scheduled event and reschedule recurring timers
    @Override
    public void fire() {
        getLogger().trace("TMR: Firing timer: %s", this);
        expired = true;
        getContext().execute(new TimerExpiration(event, getTarget()));
        if (period > 0l) {
            long delay = period;
            if (System.getProperty("io.ciera.runtime.skipMissedRecurringTimers") != null) {
                while (expiration + delay < getContext().getClock().getTime()) {
                    delay += period;
                }
            }
            schedule(delay);
        } else {
            scheduled = false;
        }
    }

    // attempt to cancel the timer
    // return true if successful
    @Override
    public boolean cancel() {
        getLogger().trace("TMR: Cancelling timer: %s", this);
        scheduled = false;
        expired = false;
        return getContext().getClock().unregisterTimer(this);
    }

    // a timer is "scheduled" from the point at which it is first scheduled
    // until it either expires and is not rescheduled (non-recurring) or is
    // cancelled
    @Override
    public boolean isScheduled() {
        return scheduled;
    }

    // a timer is expired from the point where it fires the first time until it
    // is cancelled. after the first expiration, a recurring timer is both expired
    // and cancelled.
    @Override
    public boolean isExpired() {
        return expired;
    }

    @Override
    public Duration remainingTime() {
        return new Duration(expiration - getContext().getClock().getTime());
    }

    @Override
    public TimeStamp getScheduledExpirationTime() {
        if (scheduled) {
            return new TimeStamp(expiration);
        } else {
            throw new IllegalStateException("Timer has not been scheduled: " + this);
        }
    }

    // return the last expiration time (scheduled expiration, not real time at
    // expiration)
    @Override
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
        if (target == null) {
            target = BaseApplication.getInstance().getDomain(domainClass).getEventTarget(targetId);
        }
        return target;
    }

    @Override
    public ExecutionContext getContext() {
        if (context == null) {
            context = BaseApplication.getInstance().getContext(contextId);
        }
        return context;
    }

    @Override
    public String toString() {
        return String.format("Timer[%.8s]", timerHandle);
    }

    @Override
    public long getExpiration() {
        return expiration;
    }

    @Override
    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    @Override
    public int compareTo(Timer o) {
        return Long.compare(expiration, o.getExpiration());
    }

    private Logger getLogger() {
        return getContext().getApplication().getLogger();
    }

    @Override
    public Domain getDomain() {
        return getTarget().getDomain();
    }

}
