package io.ciera.runtime.time;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Queue;

import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.time.Timer;

public class SimulatedClock extends AbstractClock {

  private long systemTime;

  public SimulatedClock() {
    this(true);
  }

  public SimulatedClock(final boolean setTime) {
    systemTime = 0;
    if (setTime) {
      systemTime = getEpoch().until(Instant.now(), ChronoUnit.NANOS);
    }
  }

  @Override
  public long getTime() {
    return systemTime;
  }

  @Override
  public void setTime(final long time) {
    systemTime = time;
  }

  @Override
  public void waitForNextTimer(final ExecutionContext context) {
    final Queue<Timer> timers = scheduledTimersMap.get(context);
    if (!timers.isEmpty()) {
      final long nextExpiration = timers.peek().getExpiration();
      setTime(nextExpiration);
    }
  }
}