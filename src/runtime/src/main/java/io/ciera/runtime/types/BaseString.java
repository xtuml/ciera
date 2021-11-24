package io.ciera.runtime.types;

import java.util.function.Function;

import io.ciera.runtime.exceptions.DeserializationException;

public class BaseString extends ModelType implements Comparable<Object> {

    private String value;

    public BaseString() {
        this("");
    }

    public BaseString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int compareTo(Object o) {
        return value.compareTo(ModelType.castTo(String.class, o));
    }

    public static <T extends Object> Function<T, ModelType> getCastFunction(Class<T> sourceType) {
        // Direct cast from a subclass
        if (BaseString.class.isAssignableFrom(sourceType)) {
            return o -> (BaseString) o;
        }

        // Special conversion for Java primitive shadow subclass
        if (String.class.equals(sourceType)) {
            return o -> new BaseString((String) o);
        }

        // Didn't find any applicable cast functions
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return ModelType.castTo(BaseString.class, o).value.equals(value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

    public static BaseString fromString(String s) {
        if (s != null) {
            return new BaseString(s);
        } else {
            throw new DeserializationException("Could not parse string", new NullPointerException());
        }
    }

}
