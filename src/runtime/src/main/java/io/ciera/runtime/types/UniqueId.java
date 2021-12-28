package io.ciera.runtime.types;

import java.util.UUID;

import io.ciera.runtime.exceptions.DeserializationException;

/**
 * The UniqueId class represents a universally unique identifier. It is
 * implemented internally by {@UUID}.
 */
public class UniqueId extends ModelType implements Comparable<UniqueId> {

    /**
     * Default value
     */
    public static final UniqueId ZERO = new UniqueId();

    private UUID id;

    public UniqueId() {
        id = new UUID(0, 0);
    }

    public UniqueId(UUID id) {
        this.id = id;
    }

    public UniqueId(UniqueId id) {
        this.id = id.id;
    }

    public static UniqueId random() {
        return new UniqueId(UUID.randomUUID());
    }

    @Override
    public int compareTo(UniqueId o) {
        return id.compareTo(o.id);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof UniqueId && id.equals(((UniqueId) o).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public static UniqueId fromString(String s) {
        try {
            return new UniqueId(UUID.fromString(s));
        } catch (IllegalArgumentException e) {
            throw new DeserializationException("Could not parse UniqueId", e);
        }
    }

}
