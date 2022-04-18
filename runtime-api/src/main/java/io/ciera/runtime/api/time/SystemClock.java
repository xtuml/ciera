package io.ciera.runtime.api.time;

import java.time.Instant;
import java.util.stream.Stream;

import io.ciera.runtime.api.application.ExecutionContext;

public interface SystemClock {

  public long getTime();

  public void setTime(long time);

  public Instant getEpoch();

  public void setEpoch(Instant epoch);

  public void checkTimers(ExecutionContext context);

  public boolean hasScheduledTimers(ExecutionContext context);

  public Stream<Timer> getScheduledTimers(ExecutionContext context);

  public boolean registerTimer(Timer timer);

  public boolean unregisterTimer(Timer timer);

  public void waitForNextTimer(ExecutionContext context) throws InterruptedException;
}
