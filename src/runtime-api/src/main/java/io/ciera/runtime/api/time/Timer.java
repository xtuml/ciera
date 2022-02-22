package io.ciera.runtime.api.time;

import java.io.Serializable;

import io.ciera.runtime.api.types.Duration;
import io.ciera.runtime.api.types.TimeStamp;

public interface Timer extends Comparable<Timer>, Serializable {

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

}
