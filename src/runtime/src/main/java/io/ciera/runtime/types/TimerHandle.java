package io.ciera.runtime.types;

import java.util.UUID;
import java.util.function.Function;

/**
 * The TimerHandle type is a special type of UniqueId used to represent an
 * {@link Timer} instance in the system.
 */
public class TimerHandle extends UniqueId {

    public TimerHandle() {
        super();
    }

    public TimerHandle(UniqueId id) {
        super(id);
    }

    public TimerHandle(UUID id) {
        super(id);
    }

    public static <T extends Object> Function<T, ModelType> getCastFunction(Class<T> sourceType) {
        // Direct cast from a subclass
        if (TimerHandle.class.isAssignableFrom(sourceType)) {
            return o -> (TimerHandle) o;
        }

        // Direct conversion from superclass
        if (UniqueId.class.equals(sourceType)) {
            return o -> new TimerHandle((UniqueId) o);
        }

        // Didn't find any applicable cast functions
        return null;
    }

    public static TimerHandle fromString(String s) {
        return new TimerHandle(UniqueId.fromString(s));
    }

}
