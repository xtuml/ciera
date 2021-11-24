package io.ciera.runtime.types;

import java.util.function.Function;

import io.ciera.runtime.exceptions.DeserializationException;

public class BaseShort extends ModelType implements Numeric {

    private short value;

    public BaseShort() {
        this((short) 0);
    }

    public BaseShort(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }

    @Override
    public int compareTo(Object o) {
        return Short.compare(value, ModelType.castTo(Short.class, o));
    }

    @Override
    public BaseShort add(Object o) {
        return new BaseShort((short) (value + ModelType.castTo(Short.class, o)));
    }

    @Override
    public BaseShort subtract(Object o) {
        return new BaseShort((short) (value - ModelType.castTo(Short.class, o)));
    }

    @Override
    public BaseShort multiply(Object o) {
        return new BaseShort((short) (value * ModelType.castTo(Short.class, o)));
    }

    @Override
    public BaseShort divide(Object o) {
        return new BaseShort((short) (value / ModelType.castTo(Short.class, o)));
    }

    @Override
    public BaseShort modulo(Object o) {
        short b = ModelType.castTo(Short.class, o);
        return new BaseShort((short) (((value % b) + b) % b));
    }

    @Override
    public BaseShort remainder(Object o) {
        return new BaseShort((short) (value % ModelType.castTo(Short.class, o)));
    }

    @Override
    public BaseShort power(Object o) {
        return new BaseShort((short) Math.pow(value, ModelType.castTo(Short.class, o)));
    }

    public static <T extends Object> Function<T, ModelType> getCastFunction(Class<T> sourceType) {
        // Direct cast from a subclass
        if (BaseShort.class.isAssignableFrom(sourceType)) {
            return o -> (BaseShort) o;
        }

        // Special conversion for Java primitive shadow subclass
        if (Short.class.equals(sourceType)) {
            return o -> new BaseShort((short) o);
        }

        // Direct conversion from superclass
        if (Numeric.class.equals(sourceType)) {
            return o -> new BaseShort((short) ((BaseNumeric) o).getWholePart());
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
        return ModelType.castTo(BaseShort.class, o).value == value;
    }

    @Override
    public int hashCode() {
        return Short.hashCode(value);
    }

    @Override
    public String toString() {
        return Short.toString(value);
    }

    public static BaseShort fromString(String s) {
        try {
            return new BaseShort(Short.parseShort(s));
        } catch (NumberFormatException e) {
            throw new DeserializationException("Could not parse short", e);
        }
    }

}
