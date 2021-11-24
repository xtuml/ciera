package io.ciera.runtime.types;

import java.util.function.Function;

import io.ciera.runtime.exceptions.DeserializationException;

public class BaseByte extends ModelType implements Numeric {

    private byte value;

    public BaseByte() {
        this((byte) 0);
    }

    public BaseByte(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    @Override
    public int compareTo(Object o) {
        return Byte.compare(value, ModelType.castTo(Byte.class, o));
    }

    @Override
    public BaseByte add(Object o) {
        return new BaseByte((byte) (value + ModelType.castTo(Byte.class, o)));
    }

    @Override
    public BaseByte subtract(Object o) {
        return new BaseByte((byte) (value - ModelType.castTo(Byte.class, o)));
    }

    @Override
    public BaseByte multiply(Object o) {
        return new BaseByte((byte) (value * ModelType.castTo(Byte.class, o)));
    }

    @Override
    public BaseByte divide(Object o) {
        return new BaseByte((byte) (value / ModelType.castTo(Byte.class, o)));
    }

    @Override
    public BaseByte modulo(Object o) {
        byte b = ModelType.castTo(Byte.class, o);
        return new BaseByte((byte) (((value % b) + b) % b));
    }

    @Override
    public BaseByte remainder(Object o) {
        return new BaseByte((byte) (value % ModelType.castTo(Byte.class, o)));
    }

    @Override
    public BaseByte power(Object o) {
        return new BaseByte((byte) Math.pow(value, ModelType.castTo(Byte.class, o)));
    }

    public static <T extends Object> Function<T, ModelType> getCastFunction(Class<T> sourceType) {
        // Direct cast from a subclass
        if (BaseByte.class.isAssignableFrom(sourceType)) {
            return o -> (BaseByte) o;
        }

        // Special conversion for Java primitive shadow subclass
        if (Byte.class.equals(sourceType)) {
            return o -> new BaseByte((byte) o);
        }

        // Direct conversion from superclass
        if (Numeric.class.equals(sourceType)) {
            return o -> new BaseByte((byte) ((BaseNumeric) o).getWholePart());
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
        return ModelType.castTo(BaseByte.class, o).value == value;
    }

    @Override
    public int hashCode() {
        return Byte.hashCode(value);
    }

    @Override
    public String toString() {
        return Byte.toString(value);
    }

    public static BaseByte fromString(String s) {
        try {
            return new BaseByte(Byte.parseByte(s));
        } catch (NumberFormatException e) {
            throw new DeserializationException("Could not parse byte", e);
        }
    }

}
