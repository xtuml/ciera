package io.ciera.runtime.summit2.domain;

import io.ciera.runtime.summit2.exceptions.DeserializationException;
import io.ciera.runtime.summit2.types.ModelType;

public abstract class ObjectInstance extends ModelType {

    @Override
    public String toString() {
        // TODO
        return "TODO";
    }

    public static ObjectInstance fromString() {
        throw new DeserializationException("Object instances cannot be deserialized.");
    }

}
