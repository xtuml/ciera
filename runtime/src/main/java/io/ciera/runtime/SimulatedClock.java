package io.ciera.runtime;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import io.ciera.runtime.api.SystemClock;

public class SimulatedClock implements SystemClock {

  private Duration offset = Duration.ZERO;

  @Override
  public Instant get() {
    return Instant.now().plus(offset);
  }

  @Override
  public void set(Instant time) {
    offset = offset.plusMillis(get().until(time, ChronoUnit.MILLIS));
  }

  @Override
  public void waitOn(Object monitor, long millis) throws InterruptedException {
    set(get().plusMillis(millis));
  }
}
