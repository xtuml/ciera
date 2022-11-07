package io.ciera.runtime;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import io.ciera.runtime.api.SystemClock;
import io.ciera.runtime.api.Timer;

public class EventTimer implements Timer {

  private static final long serialVersionUID = 1L;

  // TODO dependencies
  private final SystemClock clock = null;

  private final Duration period;
  private final Runnable action;

  private Instant expiration = null;
  private boolean scheduled = false;
  private boolean expired = false;

  public EventTimer(final Runnable action) {
    this(action, Duration.ZERO);
  }

  public EventTimer(final Runnable action, final Duration period) {
    this.action = action;
    this.period = period;
  }

  Timer schedule(final Duration delay) {
    if (delay.isNegative() && System.getProperty("io.ciera.runtime.dropNegativeDelays") != null) {
      // getLogger().trace("Dropping timer with negative delay: %s, %s", new Duration(delay), this);
      cancel();
    } else {
      expiration = expired ? expiration.plus(delay) : clock.getTime().plus(delay);
      // TODO
      // scheduled = getContext().getClock().registerTimer(this);
      // getContext().notify();
      scheduled = true;
    }
    return this;
  }

  Timer schedule(final Instant expiration) {
    return schedule(
        Duration.of(clock.getTime().until(expiration, ChronoUnit.NANOS), ChronoUnit.NANOS));
  }

  void fire() {
    // getLogger().trace("TMR: Firing timer: %s", this);
    expired = true;
    if (action != null) {
      action.run();
      if (!period.isZero()) {
        Duration delay = period;
        if (System.getProperty("io.ciera.runtime.skipMissedRecurringTimers") != null) {
          while (expiration.plus(delay).compareTo(clock.getTime()) < 0) {
            delay = delay.plus(period);
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
  public void cancel() {
    // getLogger().trace("TMR: Cancelling timer: %s", this);
    scheduled = false;
    expired = false;
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
    return Duration.of(clock.getTime().until(expiration, ChronoUnit.NANOS), ChronoUnit.NANOS);
  }

  @Override
  public Instant getScheduledExpirationTime() {
    if (scheduled) {
      return expiration;
    } else {
      throw new IllegalStateException("Timer has not been scheduled: " + this);
    }
  }

  @Override
  public Instant getLastExpirationTime() {
    if (expired) {
      return scheduled ? expiration.minus(period) : expiration;
    } else {
      throw new IllegalStateException("Timer is not expired: " + this);
    }
  }

  public Duration getPeriod() {
    return period;
  }

  @Override
  public String toString() {
    // TODO
    return String.format("Timer[%.8s]", "TODO");
  }

  @Override
  public Instant getExpiration() {
    return expiration;
  }

  @Override
  public int compareTo(final Timer o) {
    if (scheduled && o.isScheduled()) {
      return expiration.compareTo(o.getExpiration());
    } else if (scheduled && !o.isScheduled()) {
      return -1;
    } else if (!scheduled && o.isScheduled()) {
      return 1;
    } else {
      return 0;
    }
  }
}
