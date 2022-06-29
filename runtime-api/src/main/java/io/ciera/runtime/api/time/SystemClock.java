package io.ciera.runtime.api.time;

import java.time.Instant;
import java.util.stream.Stream;

import io.ciera.runtime.api.application.ExecutionContext;

public interface SystemClock {

  long getTime();

  void setTime(long time);

  Instant getEpoch();

  void setEpoch(Instant epoch);

  void checkTimers(ExecutionContext context);

  boolean hasScheduledTimers(ExecutionContext context);

  Stream<Timer> getScheduledTimers(ExecutionContext context);

  boolean registerTimer(Timer timer);

  boolean unregisterTimer(Timer timer);

  void waitForNextTimer(ExecutionContext context) throws InterruptedException;
}
