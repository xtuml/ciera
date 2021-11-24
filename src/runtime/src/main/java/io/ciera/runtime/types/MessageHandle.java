package io.ciera.runtime.types;

import java.util.UUID;
import java.util.function.Function;

/**
 * The MessageHandle type is a special type of UniqueId used to represent an
 * {@link Message} instance in the system.
 */
public class MessageHandle extends UniqueId {

    public MessageHandle() {
        super();
    }

    public MessageHandle(UniqueId id) {
        super(id);
    }

    public MessageHandle(UUID id) {
        super(id);
    }

    public static <T extends Object> Function<T, ModelType> getCastFunction(Class<T> sourceType) {
        // Direct cast from a subclass
        if (MessageHandle.class.isAssignableFrom(sourceType)) {
            return o -> (MessageHandle) o;
        }

        // Direct conversion from superclass
        if (UniqueId.class.equals(sourceType)) {
            return o -> new MessageHandle((UniqueId) o);
        }

        // Didn't find any applicable cast functions
        return null;
    }

    public static MessageHandle fromString(String s) {
        return new MessageHandle(UniqueId.fromString(s));
    }

}
