package io.ciera.runtime.time;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Queue;

import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.time.Timer;

public class WallClock extends AbstractClock {

  private final Instant STARTUP_INSTANT;
  private final long STARTUP_NANOS;
  private final long STARTUP_EPOCH_NANOS;

  private long systemTimeOffset;

  public WallClock() {
    STARTUP_INSTANT = Instant.now();
    STARTUP_NANOS = System.nanoTime();
    STARTUP_EPOCH_NANOS = getEpoch().until(STARTUP_INSTANT, ChronoUnit.NANOS);
    systemTimeOffset = 0;
  }

  @Override
  public long getTime() {
    return STARTUP_EPOCH_NANOS + System.nanoTime() - STARTUP_NANOS + systemTimeOffset;
  }

  @Override
  public void setTime(final long time) {
    systemTimeOffset += time - getTime();
  }

  @Override
  public void waitForNextTimer(final ExecutionContext context) throws InterruptedException {
    final Queue<Timer> timers = scheduledTimersMap.get(context);
    if (!timers.isEmpty()) {
      final long waitTime = timers.peek().getExpiration() - getTime();
      Thread.sleep(waitTime / 1000000L, (int) (waitTime % 1000000L));
    }
  }
}
