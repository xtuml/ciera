package io.ciera.runtime.api.time;

import java.io.Serializable;

import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.types.Duration;
import io.ciera.runtime.api.types.TimeStamp;

public interface Timer extends Comparable<Timer>, Serializable {

  public static final Timer DEFAULT = new DefaultTimer();

  public boolean schedule(long delay);

  public void fire();

  public long getExpiration();

  public void setExpiration(long expiration);

  public boolean cancel();

  // a timer is "scheduled" from the point at which it is first scheduled
  // until it either expires and is not rescheduled (non-recurring) or is
  // cancelled
  public boolean isScheduled();

  // a timer is expired from the point where it fires the first time until it
  // is cancelled. after the first expiration, a recurring timer is both expired
  // and cancelled.
  public boolean isExpired();

  public Duration remainingTime();

  public TimeStamp getScheduledExpirationTime();

  // return the last expiration time (scheduled expiration, not real time at
  // expiration)
  public TimeStamp getLastExpirationTime();

  public Domain getDomain();

  public ExecutionContext getContext();

  static class DefaultTimer implements Timer {

    private static final long serialVersionUID = 1L;

    @Override
    public int compareTo(Timer o) {
      return 0;
    }

    @Override
    public boolean schedule(long delay) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void fire() {
      throw new UnsupportedOperationException();
    }

    @Override
    public long getExpiration() {
      throw new UnsupportedOperationException();
    }

    @Override
    public void setExpiration(long expiration) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean cancel() {
      return false;
    }

    @Override
    public boolean isScheduled() {
      return false;
    }

    @Override
    public boolean isExpired() {
      return false;
    }

    @Override
    public Duration remainingTime() {
      throw new UnsupportedOperationException();
    }

    @Override
    public TimeStamp getScheduledExpirationTime() {
      throw new UnsupportedOperationException();
    }

    @Override
    public TimeStamp getLastExpirationTime() {
      throw new UnsupportedOperationException();
    }

    @Override
    public Domain getDomain() {
      throw new UnsupportedOperationException();
    }

    @Override
    public ExecutionContext getContext() {
      throw new UnsupportedOperationException();
    }
  }
}
