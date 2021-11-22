package io.ciera.runtime.summit2.types;

import java.util.function.Function;

/**
 * TODO Used only for casting purposes
 *
 */
public class BaseNumeric extends ModelType implements Numeric {

    private long wholePart;
    private double fractionalPart;

    public BaseNumeric(long intValue) {
        this(intValue, 0d);
    }

    public BaseNumeric(double realValue) {
        this(Double.valueOf(realValue).longValue(), Math.abs(realValue) % 1);
    }

    public BaseNumeric(long wholePart, double fractionalPart) {
        this.wholePart = wholePart;
        this.fractionalPart = fractionalPart;
    }

    public long getWholePart() {
        return wholePart;
    }

    public double getFractionalPart() {
        return fractionalPart;
    }

    public static <T extends Object> Function<T, ModelType> getCastFunction(Class<T> sourceType) {

        // Direct casting from subclasses:
        // This is a special case because the numeric type is abstract and only
        // exists to bridge between other numeric types
        if (BaseLong.class.equals(sourceType)) {
            return o -> new BaseNumeric(((BaseLong) o).getValue());

        }
        // TODO more subclasses

        // Didn't find any applicable cast functions
        return null;
    }

    @Override
    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Comparison not defined for abstract type 'Numeric'");
    }

    @Override
    public Numeric add(Object o) {
        throw new UnsupportedOperationException("Addition not defined for abstract type 'Numeric'");
    }

    @Override
    public Numeric subtract(Object o) {
        throw new UnsupportedOperationException("Subtraction not defined for abstract type 'Numeric'");
    }

    @Override
    public Numeric multiply(Object o) {
        throw new UnsupportedOperationException("Multiplication not defined for abstract type 'Numeric'");
    }

    @Override
    public Numeric divide(Object o) {
        throw new UnsupportedOperationException("Division not defined for abstract type 'Numeric'");
    }

    @Override
    public Numeric modulo(Object o) {
        throw new UnsupportedOperationException("Modulo not defined for abstract type 'Numeric'");
    }

    @Override
    public Numeric remainder(Object o) {
        throw new UnsupportedOperationException("Remainder not defined for abstract type 'Numeric'");
    }

    @Override
    public Numeric power(Object o) {
        throw new UnsupportedOperationException("Power not defined for abstract type 'Numeric'");
    }

}