package io.ciera.runtime.summit2.types;

import java.util.function.Function;

import io.ciera.runtime.summit2.exceptions.DeserializationException;

public class BaseInteger extends ModelType implements Numeric {

    private int value;

    public BaseInteger() {
        this(0);
    }

    public BaseInteger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare(value, ModelType.castTo(Integer.class, o));
    }

    @Override
    public BaseInteger add(Object o) {
        return new BaseInteger(value + ModelType.castTo(Integer.class, o));
    }

    @Override
    public BaseInteger subtract(Object o) {
        return new BaseInteger(value - ModelType.castTo(Integer.class, o));
    }

    @Override
    public BaseInteger multiply(Object o) {
        return new BaseInteger(value * ModelType.castTo(Integer.class, o));
    }

    @Override
    public BaseInteger divide(Object o) {
        return new BaseInteger(value / ModelType.castTo(Integer.class, o));
    }

    @Override
    public BaseInteger modulo(Object o) {
        int b = ModelType.castTo(Integer.class, o);
        return new BaseInteger((((value % b) + b) % b));
    }

    @Override
    public BaseInteger remainder(Object o) {
        return new BaseInteger(value % ModelType.castTo(Integer.class, o));
    }

    @Override
    public BaseInteger power(Object o) {
        return new BaseInteger((int) Math.pow(value, ModelType.castTo(Integer.class, o)));
    }

    public static <T extends Object> Function<T, ModelType> getCastFunction(Class<T> sourceType) {
        // Direct cast from a subclass
        if (BaseInteger.class.isAssignableFrom(sourceType)) {
            return o -> (BaseInteger) o;
        }

        // Special conversion for Java primitive shadow subclass
        if (Integer.class.equals(sourceType)) {
            return o -> new BaseInteger((int) o);
        }

        // Direct conversion from superclass
        if (Numeric.class.equals(sourceType)) {
            return o -> new BaseInteger((int) ((BaseNumeric) o).getWholePart());
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
        return ModelType.castTo(BaseInteger.class, o).value == value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    public static BaseInteger fromString(String s) {
        try {
            return new BaseInteger(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            throw new DeserializationException("Could not parse integer", e);
        }
    }

}
