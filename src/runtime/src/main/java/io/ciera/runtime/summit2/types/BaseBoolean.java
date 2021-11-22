package io.ciera.runtime.summit2.types;

import java.util.function.Function;

public class BaseBoolean extends ModelType implements Comparable<Object> {

    private boolean value;

    public BaseBoolean() {
        this(false);
    }

    public BaseBoolean(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public int compareTo(Object o) {
        return Boolean.compare(value, ModelType.castTo(Boolean.class, o));
    }

    public static <T extends Object> Function<T, ModelType> getCastFunction(Class<T> sourceType) {
        // Direct cast from a subclass
        if (BaseBoolean.class.isAssignableFrom(sourceType)) {
            return o -> (BaseBoolean) o;
        }

        // Special conversion for Java primitive shadow subclass
        if (Boolean.class.equals(sourceType)) {
            return o -> new BaseBoolean((boolean) o);
        }

        // Didn't find any applicable cast functions
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return ModelType.castTo(BaseBoolean.class, o).value == value;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(value);
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    public static BaseBoolean fromString(String s) {
        return new BaseBoolean(Boolean.parseBoolean(s));
    }

}
