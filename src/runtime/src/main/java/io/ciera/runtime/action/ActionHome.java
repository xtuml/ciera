package io.ciera.runtime.action;

import io.ciera.runtime.application.Event;
import io.ciera.runtime.application.EventTarget;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;
import io.ciera.runtime.application.Timer;
import io.ciera.runtime.domain.Domain;
import io.ciera.runtime.types.Duration;
import io.ciera.runtime.types.TimeStamp;

/**
 * An action home represents an element that can contain generated action
 * language statements. The action home provides a standard way to access the
 * domain (for the purpose of accessing domain resources such as relators,
 * terminator messages, utilities, etc). It also gives access to the execution
 * context for time and event generation.
 */
public interface ActionHome {

    /**
     * Get the parent domain.
     * 
     * @return {@link Domain} the parent domain within which the actions are
     *         defined.
     */
    public Domain getDomain();

    /**
     * Get the logger instance.
     * 
     * @return {@link Logger} the logger for this application.
     */
    public Logger getLogger();

    /**
     * Get the execution context.
     * 
     * @return {@link ExecutionContext} the execution context within which the
     *         actions run.
     */
    public ExecutionContext getContext();

    public default <E extends Event> void _generateEvent(Class<E> eventType, EventTarget target, Object... data) {
        getContext().generateEvent(eventType, target, data);
    }

    public default <E extends Event> void _generateEventToSelf(Class<E> eventType, EventTarget target, Object... data) {
        getContext().generateEventToSelf(eventType, target, data);
    }

    public default <E extends Event> Timer _scheduleEvent(Class<E> eventType, EventTarget target, Duration delay,
            Object... eventData) {
        return getContext().scheduleEvent(eventType, target, delay, eventData);
    }

    public default <E extends Event> Timer _scheduleEvent(Class<E> eventType, EventTarget target, TimeStamp expiration,
            Object... eventData) {
        return getContext().scheduleEvent(eventType, target, expiration, eventData);
    }

    public default <E extends Event> Timer _scheduleRecurringEvent(Class<E> eventType, EventTarget target,
            Duration delay, Duration period, Object... eventData) {
        return getContext().scheduleRecurringEvent(eventType, target, delay, period, eventData);
    }

    public default <E extends Event> Timer _scheduleRecurringEvent(Class<E> eventType, EventTarget target,
            TimeStamp expiration, Duration period, Object... eventData) {
        return getContext().scheduleRecurringEvent(eventType, target, expiration, period, eventData);
    }

    public default void _halt() {
        getContext().halt();
    }

    @Deprecated
    public default void _delay(Duration delay) {
        getContext().delay(delay);
    }

}
