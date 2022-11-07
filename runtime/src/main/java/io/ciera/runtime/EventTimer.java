package io.ciera.runtime;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimerTask;

import io.ciera.runtime.api.Timer;

public class EventTimer implements Timer {

  private static final long serialVersionUID = 1L;

  private static final java.util.Timer internalTimer = new java.util.Timer();

  private final TimerTask action;
  private Instant scheduledExpiration;
  private Instant expiredAt;

  public EventTimer(Runnable action) {
    this.action =
        new TimerTask() {
          @Override
          public void run() {
            expiredAt = Instant.now();
            try {
              action.run();
            } catch (RuntimeException e) {
              e.printStackTrace();
              System.exit(2);
            }
          }
        };
    this.scheduledExpiration = null;
    this.expiredAt = null;
  }

  public EventTimer(Runnable action, Duration period) {
    this(action);
  }

  public Timer schedule(final Duration delay) {
    scheduledExpiration = Instant.now().plus(delay);
    internalTimer.schedule(action, delay.toMillis());
    return this;
  }

  public Timer schedule(final Instant expiration) {
    scheduledExpiration = expiration;
    internalTimer.schedule(action, Date.from(expiration));
    return this;
  }

  @Override
  public Instant getExpiration() {
    if (isExpired()) {
      return expiredAt;
    } else {
      throw new RuntimeException("Timer has not expired");
    }
  }

  @Override
  public void cancel() {
    action.cancel();
  }

  // a timer is "scheduled" from the point at which it is first scheduled
  // until it either expires and is not rescheduled (non-recurring) or is
  // cancelled
  @Override
  public boolean isScheduled() {
    return scheduledExpiration != null;
  }

  // a timer is expired from the point where it fires the first time until it
  // is cancelled. after the first expiration, a recurring timer is both expired
  // and cancelled.
  @Override
  public boolean isExpired() {
    return expiredAt != null;
  }

  @Override
  public Duration remainingTime() {
    if (isScheduled()) {
      return Duration.of(
          Instant.now().until(getScheduledExpirationTime(), ChronoUnit.MICROS), ChronoUnit.MICROS);
    } else {
      throw new RuntimeException("Timer is not scheduled");
    }
  }

  @Override
  public Instant getScheduledExpirationTime() {
    if (isScheduled()) {
      return scheduledExpiration;
    } else {
      throw new RuntimeException("Timer has not expired");
    }
  }

  // return the last expiration time (scheduled expiration, not real time at
  // expiration)
  @Override
  public Instant getLastExpirationTime() {
    return getExpiration();
  }

  @Override
  public int compareTo(Timer t) {
    return remainingTime().compareTo(t.remainingTime());
  }
}
