package io.ciera.runtime.api;

import java.time.Instant;
import java.util.function.Supplier;

public interface SystemClock extends Supplier<Instant> {

  default void set(Instant newTime) {}

  default void sleep(long millis) throws InterruptedException {
    Thread.sleep(millis);
  }
}
