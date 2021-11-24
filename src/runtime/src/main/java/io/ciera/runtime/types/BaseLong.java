package io.ciera.runtime.types;

import java.util.function.Function;

import io.ciera.runtime.exceptions.DeserializationException;

public class BaseLong extends ModelType implements Numeric {

    private long value;

    public BaseLong() {
        this(0l);
    }

    public BaseLong(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public int compareTo(Object o) {
        return Long.compare(value, ModelType.castTo(Long.class, o));
    }

    @Override
    public BaseLong add(Object o) {
        return new BaseLong(value + ModelType.castTo(Long.class, o));
    }

    @Override
    public BaseLong subtract(Object o) {
        return new BaseLong(value - ModelType.castTo(Long.class, o));
    }

    @Override
    public BaseLong multiply(Object o) {
        return new BaseLong(value * ModelType.castTo(Long.class, o));
    }

    @Override
    public BaseLong divide(Object o) {
        return new BaseLong(value / ModelType.castTo(Long.class, o));
    }

    @Override
    public BaseLong modulo(Object o) {
        long b = ModelType.castTo(Long.class, o);
        return new BaseLong((((value % b) + b) % b));
    }

    @Override
    public BaseLong remainder(Object o) {
        return new BaseLong(value % ModelType.castTo(Long.class, o));
    }

    @Override
    public BaseLong power(Object o) {
        return new BaseLong((long) Math.pow(value, ModelType.castTo(Long.class, o)));
    }

    public static <T extends Object> Function<T, ModelType> getCastFunction(Class<T> sourceType) {
        // Direct cast from a subclass
        if (BaseLong.class.isAssignableFrom(sourceType)) {
            return o -> (BaseLong) o;
        }

        // Special conversion for Java primitive shadow subclass
        if (Long.class.equals(sourceType)) {
            return o -> new BaseLong((long) o);
        }

        // Direct conversion from superclass
        if (Numeric.class.equals(sourceType)) {
            return o -> new BaseLong(((BaseNumeric) o).getWholePart());
        }

        // Search in superclass for indirect conversion
        Function<T, ModelType> f = BaseNumeric.getCastFunction(sourceType);
        if (f != null) {
            return o -> getCastFunction(Numeric.class).apply((Numeric) f.apply(o));
        }

        // Didn't find any applicable cast functions
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return ModelType.castTo(BaseLong.class, o).value == value;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(value);
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

    public static BaseLong fromString(String s) {
        try {
            return new BaseLong(Long.parseLong(s));
        } catch (NumberFormatException e) {
            throw new DeserializationException("Could not parse long", e);
        }
    }

}
