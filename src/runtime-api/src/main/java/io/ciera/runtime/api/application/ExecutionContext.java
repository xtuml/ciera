package io.ciera.runtime.api.application;

import java.util.concurrent.Executor;

import io.ciera.runtime.api.time.SystemClock;
import io.ciera.runtime.api.time.Timer;
import io.ciera.runtime.api.types.Duration;
import io.ciera.runtime.api.types.TimeStamp;

public interface ExecutionContext extends Executor {

    public String getName();

    public Application getApplication();

    public <E extends Event> void generateEvent(Class<E> eventType, EventTarget target, Object... data);

    public <E extends Event> void generateEventToSelf(Class<E> eventType, EventTarget target, Object... data);

    public <E extends Event> Timer scheduleEvent(Class<E> eventType, EventTarget target, Duration delay,
            Object... eventData);

    public <E extends Event> Timer scheduleEvent(Class<E> eventType, EventTarget target, TimeStamp expiration,
            Object... eventData);

    public <E extends Event> Timer scheduleRecurringEvent(Class<E> eventType, EventTarget target, Duration delay,
            Duration period, Object... eventData);

    public <E extends Event> Timer scheduleRecurringEvent(Class<E> eventType, EventTarget target, TimeStamp expiration,
            Duration period, Object... eventData);

    public void halt();

    @Deprecated
    public void delay(Duration delay);

    public ExecutionMode getExecutionMode();

    public ModelIntegrityMode getModelIntegrityMode();

    public SystemClock getClock();

    public enum ExecutionMode {

        INTERLEAVED, SEQUENTIAL;

    }

    public enum ModelIntegrityMode {

        STRICT, RELAXED, OFF;

    }

}
