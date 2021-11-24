package io.ciera.runtime.types;

import java.util.UUID;
import java.util.function.Function;

/**
 * The EventHandle type is a special type of UniqueId used to represent an
 * {@link Event} instance in the system.
 */
public class EventHandle extends UniqueId {

    public EventHandle() {
        super();
    }

    public EventHandle(UniqueId id) {
        super(id);
    }

    public EventHandle(UUID id) {
        super(id);
    }

    public static <T extends Object> Function<T, ModelType> getCastFunction(Class<T> sourceType) {
        // Direct cast from a subclass
        if (EventHandle.class.isAssignableFrom(sourceType)) {
            return o -> (EventHandle) o;
        }

        // Direct conversion from superclass
        if (UniqueId.class.equals(sourceType)) {
            return o -> new EventHandle((UniqueId) o);
        }

        // Didn't find any applicable cast functions
        return null;
    }

    public static EventHandle fromString(String s) {
        return new EventHandle(UniqueId.fromString(s));
    }

}
