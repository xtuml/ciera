package io.ciera.runtime.types;

import java.util.function.Function;

import io.ciera.runtime.exceptions.DeserializationException;

public class BaseDouble extends ModelType implements Numeric {

    private double value;

    public BaseDouble() {
        this(0d);
    }

    public BaseDouble(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public int compareTo(Object o) {
        return Double.compare(value, ModelType.castTo(Double.class, o));
    }

    @Override
    public BaseDouble add(Object o) {
        return new BaseDouble(value + ModelType.castTo(Double.class, o));
    }

    @Override
    public BaseDouble subtract(Object o) {
        return new BaseDouble(value - ModelType.castTo(Double.class, o));
    }

    @Override
    public BaseDouble multiply(Object o) {
        return new BaseDouble(value * ModelType.castTo(Double.class, o));
    }

    @Override
    public BaseDouble divide(Object o) {
        return new BaseDouble(value / ModelType.castTo(Double.class, o));
    }

    @Override
    public BaseDouble modulo(Object o) {
        double b = ModelType.castTo(Double.class, o);
        return new BaseDouble((((value % b) + b) % b));
    }

    @Override
    public BaseDouble remainder(Object o) {
        return new BaseDouble(value % ModelType.castTo(Double.class, o));
    }

    @Override
    public BaseDouble power(Object o) {
        return new BaseDouble(Math.pow(value, ModelType.castTo(Double.class, o)));
    }

    public static <T extends Object> Function<T, ModelType> getCastFunction(Class<T> sourceType) {
        // Direct cast from a subclass
        if (BaseDouble.class.isAssignableFrom(sourceType)) {
            return o -> (BaseDouble) o;
        }

        // Special conversion for Java primitive shadow subclass
        if (Double.class.equals(sourceType)) {
            return o -> new BaseDouble((double) o);
        }

        // Direct conversion from superclass
        if (Numeric.class.equals(sourceType)) {
            return o -> new BaseDouble(((BaseNumeric) o).getWholePart());
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
        return ModelType.castTo(BaseDouble.class, o).value == value;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    public static BaseDouble fromString(String s) {
        try {
            return new BaseDouble(Double.parseDouble(s));
        } catch (NumberFormatException e) {
            throw new DeserializationException("Could not parse double", e);
        }
    }

}
