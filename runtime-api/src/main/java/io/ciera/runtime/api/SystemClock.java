package io.ciera.runtime.api;

import java.time.Instant;

public interface SystemClock {

  Instant getTime();

  default void setTime(Instant time) {
    throw new UnsupportedOperationException();
  }

  // TODO set epoch
}
