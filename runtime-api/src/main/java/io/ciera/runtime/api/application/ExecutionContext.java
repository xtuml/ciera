package io.ciera.runtime.api.application;

import java.util.concurrent.Executor;
import java.util.function.Function;

import io.ciera.runtime.api.time.SystemClock;
import io.ciera.runtime.api.time.Timer;
import io.ciera.runtime.api.types.Duration;
import io.ciera.runtime.api.types.TimeStamp;

public interface ExecutionContext extends Executor {

  public String getName();

  public Application getApplication();

  public <E extends Event> void generateEvent(
      Function<Object[], E> eventBuilder, EventTarget target, Object... data);

  public <E extends Event> void generateEventToSelf(
      Function<Object[], E> eventBuilder, EventTarget target, Object... data);

  public <E extends Event> Timer scheduleEvent(
      Function<Object[], E> eventBuilder, EventTarget target, Duration delay, Object... eventData);

  public <E extends Event> Timer scheduleEvent(
      Function<Object[], E> eventBuilder,
      EventTarget target,
      TimeStamp expiration,
      Object... eventData);

  public Timer scheduleAction(Duration delay, Runnable action);

  public Timer scheduleAction(TimeStamp expiration, Runnable action);

  public <E extends Event> Timer scheduleRecurringEvent(
      Function<Object[], E> eventBuilder,
      EventTarget target,
      Duration delay,
      Duration period,
      Object... eventData);

  public <E extends Event> Timer scheduleRecurringEvent(
      Function<Object[], E> eventBuilder,
      EventTarget target,
      TimeStamp expiration,
      Duration period,
      Object... eventData);

  public Timer scheduleRecurringAction(Duration delay, Duration period, Runnable action);

  public Timer scheduleRecurringAction(TimeStamp expiration, Duration period, Runnable action);

  public void halt();

  @Deprecated
  public void delay(Duration delay);

  public ExecutionMode getExecutionMode();

  public ModelIntegrityMode getModelIntegrityMode();

  public SystemClock getClock();

  public enum ExecutionMode {
    INTERLEAVED,
    SEQUENTIAL;
  }

  public enum ModelIntegrityMode {
    STRICT,
    RELAXED,
    OFF;
  }
}
