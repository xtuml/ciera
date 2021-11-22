package io.ciera.runtime.summit2.types;

import java.util.function.Function;

/**
 * The BaseNumeric class is a concrete base implementation of the
 * {@link Numeric} interface. Its only purpose in life is to provide a path for
 * type conversion between different numeric types such as conversions between
 * real and integer without losing information.
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
        if (BaseByte.class.isAssignableFrom(sourceType)) {
            return o -> new BaseNumeric(((BaseByte) o).getValue());
        } else if (Byte.class.equals(sourceType)) {
            return o -> new BaseNumeric(Byte.valueOf((byte) o).longValue());
        } else if (BaseDouble.class.isAssignableFrom(sourceType)) {
            return o -> new BaseNumeric(((BaseDouble) o).getValue());
        } else if (Double.class.equals(sourceType)) {
            return o -> new BaseNumeric((double) o);
        } else if (BaseInteger.class.isAssignableFrom(sourceType)) {
            return o -> new BaseNumeric(((BaseInteger) o).getValue());
        } else if (Integer.class.equals(sourceType)) {
            return o -> new BaseNumeric(Integer.valueOf((int) o).longValue());
        } else if (BaseLong.class.isAssignableFrom(sourceType)) {
            return o -> new BaseNumeric(((BaseLong) o).getValue());
        } else if (Long.class.equals(sourceType)) {
            return o -> new BaseNumeric((long) o);
        } else if (BaseShort.class.isAssignableFrom(sourceType)) {
            return o -> new BaseNumeric(((BaseShort) o).getValue());
        } else if (Short.class.equals(sourceType)) {
            return o -> new BaseNumeric(Short.valueOf((short) o).longValue());
        }

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