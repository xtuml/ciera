package io.ciera.runtime.api;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;

public interface Timer extends Comparable<Timer>, Serializable {

  Timer DEFAULT = null;

  boolean schedule(long delay);

  void fire();

  Instant getExpiration();

  boolean cancel();

  // a timer is "scheduled" from the point at which it is first scheduled
  // until it either expires and is not rescheduled (non-recurring) or is
  // cancelled
  boolean isScheduled();

  // a timer is expired from the point where it fires the first time until it
  // is cancelled. after the first expiration, a recurring timer is both expired
  // and cancelled.
  boolean isExpired();

  Duration remainingTime();

  Instant getScheduledExpirationTime();

  // return the last expiration time (scheduled expiration, not real time at
  // expiration)
  Instant getLastExpirationTime();
}
