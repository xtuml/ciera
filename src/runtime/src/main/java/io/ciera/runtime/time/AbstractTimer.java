package io.ciera.runtime.time;

import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.application.Logger;
import io.ciera.runtime.api.time.Timer;
import io.ciera.runtime.api.types.Duration;
import io.ciera.runtime.api.types.TimeStamp;
import io.ciera.runtime.api.types.UniqueId;
import io.ciera.runtime.application.task.Task;

public abstract class AbstractTimer implements Timer {

  private static final long serialVersionUID = 1L;

  private final UniqueId timerHandle;
  private final String contextId;
  private transient ExecutionContext context;
  private final long period;
  private long expiration;
  private boolean scheduled;
  private boolean expired;

  public AbstractTimer(ExecutionContext context) {
    this(UniqueId.random(), context, Duration.ZERO);
  }

  public AbstractTimer(ExecutionContext context, Duration period) {
    this(UniqueId.random(), context, period);
  }

  public AbstractTimer(UniqueId timerHandle, ExecutionContext context, Duration period) {
    this.timerHandle = timerHandle;
    this.context = context;
    this.contextId = context.getName();
    this.period = period.getValue();
    this.expiration = 0;
    this.scheduled = false;
    this.expired = false;
  }

  protected abstract Task getAction();

  @Override
  public boolean schedule(long delay) {
    getLogger()
        .trace(
            "TMR: Scheduling timer: %s at %s",
            this, new TimeStamp(getContext().getClock().getTime() + delay));
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

  @Override
  public void fire() {
    getLogger().trace("TMR: Firing timer: %s", this);
    expired = true;
    Task action = getAction();
    if (action != null) {
      getContext().execute(action);
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
    } else {
      scheduled = false;
    }
  }

  @Override
  public boolean cancel() {
    getLogger().trace("TMR: Cancelling timer: %s", this);
    scheduled = false;
    expired = false;
    return getContext().getClock().unregisterTimer(this);
  }

  @Override
  public boolean isScheduled() {
    return scheduled;
  }

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

  @Override
  public ExecutionContext getContext() {
    if (context == null) {
      context = Application.getInstance().getContext(contextId);
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
}
