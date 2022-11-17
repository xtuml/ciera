package io.ciera.runtime;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;
import java.util.function.Supplier;

import io.ciera.runtime.api.Architecture;
import io.ciera.runtime.api.Event;
import io.ciera.runtime.api.EventTarget;
import io.ciera.runtime.api.Timer;

public class DelayedEvent implements Timer {

  private static final long serialVersionUID = 1L;

  private final Architecture arch = Architecture.getInstance();
  private final Supplier<Instant> clock = arch.getClock();

  private final Function<Object[], Event> eventBuilder;
  private final Object[] eventData;
  private final EventTarget target;
  private final Duration period;

  private Instant scheduledExpiration = null;
  private Instant expiredAt = null;

  public DelayedEvent(
      final Function<Object[], Event> eventBuilder,
      final Object[] eventData,
      final EventTarget target,
      final Duration period) {
    this.eventBuilder = eventBuilder;
    this.eventData = eventData;
    this.target = target;
    this.period = period;
  }

  public DelayedEvent(
      final Function<Object[], Event> eventBuilder,
      final Object[] eventData,
      final EventTarget target) {
    this(eventBuilder, eventData, target, null);
  }

  public void expireNow() {
    target.queueEvent(eventBuilder.apply(eventData));
    expiredAt = clock.get();
    if (period != null) {
      schedule(scheduledExpiration.plus(period)); // reschedule recurring timer
    } else {
      scheduledExpiration = null;
    }
  }

  public Timer schedule(final Duration delay) {
    return schedule(clock.get().plus(delay));
  }

  public Timer schedule(final Instant expiration) {
    scheduledExpiration = expiration;
    target.queueDelayedEvent(this);
    return this;
  }

  @Override
  public void cancel() {
    scheduledExpiration = null;
    expiredAt = null;
    target.cancelDelayedEvent(this);
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
  // and scheduled.
  @Override
  public boolean isExpired() {
    return expiredAt != null;
  }

  @Override
  public Duration remainingTime() {
    if (isScheduled()) {
      return Duration.ofMillis(clock.get().until(getScheduledExpirationTime(), ChronoUnit.MILLIS));
    } else {
      throw new RuntimeException("Timer is not scheduled"); // TODO
    }
  }

  @Override
  public Instant getScheduledExpirationTime() {
    if (isScheduled()) {
      return scheduledExpiration;
    } else {
      throw new RuntimeException("Timer is not scheduled"); // TODO
    }
  }

  // return the last expiration time (scheduled expiration, not real time at
  // expiration)
  @Override
  public Instant getLastExpirationTime() {
    if (isExpired()) {
      return expiredAt;
    } else {
      throw new RuntimeException("Timer has not expired"); // TODO
    }
  }

  @Override
  public int compareTo(Timer t) {
    return remainingTime().compareTo(t.remainingTime());
  }
}
