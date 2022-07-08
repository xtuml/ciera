package io.ciera.runtime;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;

import io.ciera.runtime.api.SystemClock;

public class WallClock implements SystemClock {

  private TemporalAdjuster systemTimeOffset = t -> t;

  @Override
  public Instant getTime() {
    return Instant.now().with(systemTimeOffset);
  }

  @Override
  public void setTime(final Instant time) {
    final var currentClock = getTime();
    systemTimeOffset = t -> t.plus(currentClock.until(time, ChronoUnit.NANOS), ChronoUnit.NANOS);
  }
}
