package io.ciera.runtime.api;

import java.time.Instant;

public interface SystemClock {

  Instant getTime();

  void setTime(Instant time);

  Instant getEpoch();

  void setEpoch(Instant epoch);
}
