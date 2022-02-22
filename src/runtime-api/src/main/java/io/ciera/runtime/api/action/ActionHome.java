package io.ciera.runtime.api.action;

import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.time.SystemClock;
import io.ciera.runtime.api.time.Timer;
import io.ciera.runtime.api.types.Duration;
import io.ciera.runtime.api.types.TimeStamp;

/**
 * An action home represents an element that can contain generated action
 * language statements. The action home provides a standard way to access the
 * domain (for the purpose of accessing domain resources such as relators,
 * terminator messages, utilities, etc). It also gives access to the execution
 * context for time and event generation.
 */
public interface ActionHome extends ExecutionContext {

    /**
     * Get the parent domain.
     * 
     * @return {@link Domain} the parent domain within which the actions are
     *         defined.
     */
    public Domain getDomain();

    /**
     * Get the execution context.
     * 
     * @return {@link ExecutionContext} the execution context within which the
     *         actions run.
     */
    public ExecutionContext getContext();

    @Override
    public default String getName() {
        return getContext().getName();
    }

    @Override
    public default Application getApplication() {
        return getContext().getApplication();
    }

    @Override
    public default <E extends Event> void generateEvent(Class<E> eventType, EventTarget target, Object... data) {
        getContext().generateEvent(eventType, target, data);
    }

    @Override
    public default <E extends Event> void generateEventToSelf(Class<E> eventType, EventTarget target, Object... data) {
        getContext().generateEventToSelf(eventType, target, data);
    }

    @Override
    public default <E extends Event> Timer scheduleEvent(Class<E> eventType, EventTarget target, Duration delay,
            Object... eventData) {
        return getContext().scheduleEvent(eventType, target, delay, eventData);
    }

    @Override
    public default <E extends Event> Timer scheduleEvent(Class<E> eventType, EventTarget target, TimeStamp expiration,
            Object... eventData) {
        return getContext().scheduleEvent(eventType, target, expiration, eventData);
    }

    @Override
    public default <E extends Event> Timer scheduleRecurringEvent(Class<E> eventType, EventTarget target,
            Duration delay, Duration period, Object... eventData) {
        return getContext().scheduleRecurringEvent(eventType, target, delay, period, eventData);
    }

    @Override
    public default <E extends Event> Timer scheduleRecurringEvent(Class<E> eventType, EventTarget target,
            TimeStamp expiration, Duration period, Object... eventData) {
        return getContext().scheduleRecurringEvent(eventType, target, expiration, period, eventData);
    }

    @Override
    public default void halt() {
        getContext().halt();
    }

    @Override
    public default void execute(Runnable command) {
        getContext().execute(command);
    }

    @Override
    @Deprecated
    public default void delay(Duration delay) {
        getContext().delay(delay);
    }

    @Override
    public default ExecutionMode getExecutionMode() {
        return getContext().getExecutionMode();
    }

    @Override
    public default ModelIntegrityMode getModelIntegrityMode() {
        return getContext().getModelIntegrityMode();
    }

    @Override
    public default SystemClock getClock() {
        return getContext().getClock();
    }

}
