package io.ciera.runtime.api;

import java.time.Instant;
import java.util.function.Supplier;

public interface SystemClock extends Supplier<Instant> {

  default void set(Instant newTime) {}

  default void waitOn(Object monitor, long millis) throws InterruptedException {
    if (monitor == null) {
      Thread.sleep(millis);
    } else {
      synchronized (monitor) {
        monitor.wait(millis);
      }
    }
  }
}
