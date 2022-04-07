package io.ciera.runtime.api.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.domain.Domain;
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
public interface ActionHome {

    /**
     * Get the parent domain.
     * 
     * @return {@link Domain} the parent domain within which the actions are
     *         defined.
     */
    public Domain getDomain();
    
    default public Domain getDomain(String domainName) {
        return getApplication().getDomain(domainName);
    }

    /**
     * Get the execution context.
     * 
     * @return {@link ExecutionContext} the execution context within which the
     *         actions run.
     */
    public ExecutionContext getContext();

    public default Application getApplication() {
        return getContext().getApplication();
    }

    public default <E extends Event> void generateEvent(Class<E> eventType, EventTarget target, Object... data) {
        getContext().generateEvent(eventType, target, data);
    }

    public default <E extends Event> void generateEventToSelf(Class<E> eventType, EventTarget target, Object... data) {
        getContext().generateEventToSelf(eventType, target, data);
    }

    public default <E extends Event> Timer scheduleEvent(Class<E> eventType, EventTarget target, Duration delay,
            Object... eventData) {
        return getContext().scheduleEvent(eventType, target, delay, eventData);
    }

    public default <E extends Event> Timer scheduleEvent(Class<E> eventType, EventTarget target, TimeStamp expiration,
            Object... eventData) {
        return getContext().scheduleEvent(eventType, target, expiration, eventData);
    }
    
    public default Timer scheduleAction(Duration delay, Runnable action) {
        return getContext().scheduleAction(delay, action);
    }
    
    public default Timer scheduleAction(TimeStamp expiration, Runnable action) {
        return getContext().scheduleAction(expiration, action);
    }

    public default <E extends Event> Timer scheduleRecurringEvent(Class<E> eventType, EventTarget target,
            Duration delay, Duration period, Object... eventData) {
        return getContext().scheduleRecurringEvent(eventType, target, delay, period, eventData);
    }

    public default <E extends Event> Timer scheduleRecurringEvent(Class<E> eventType, EventTarget target,
            TimeStamp expiration, Duration period, Object... eventData) {
        return getContext().scheduleRecurringEvent(eventType, target, expiration, period, eventData);
    }
    
    public default Timer scheduleRecurringAction(Duration delay, Duration period, Runnable action) {
        return getContext().scheduleRecurringAction(delay, period, action);
    }
    
    public default Timer scheduleRecurringAction(TimeStamp expiration, Duration period, Runnable action) {
        return getContext().scheduleRecurringAction(expiration, period, action);
    }

    public default void halt() {
        getContext().halt();
    }

    @Deprecated
    public default void delay(Duration delay) {
        getContext().delay(delay);
    }
    
    public default <E> List<E> newList() {
        return new ArrayList<>();
    }
    
    public default <E> List<E> newList(Collection<? extends E> c) {
        return new ArrayList<>(c);
    }
    
    public default <E> Set<E> newSet() {
        return new LinkedHashSet<>();
    }
    
    public default <E> Set<E> newSet(Collection<? extends E> c) {
        return new LinkedHashSet<>(c);
    }
    
    public default <K, V> Map<K, V> newMap() {
        return new HashMap<>();
    }
    
    public default <K, V> Map<K, V> newMap(Map<? extends K, ? extends V> m) {
        return new HashMap<>(m);
    }
    
}
