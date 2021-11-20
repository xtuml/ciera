package io.ciera.runtime.summit2.types;

import java.util.function.Function;

/**
 * TODO Used only for casting purposes
 *
 */
public class BaseNumeric extends ModelType implements Numeric, Comparable<Object> {

    private long wholePart;
    private double fractionalPart;

    public BaseNumeric(long wholePart, double fractionalPart) {
        this.wholePart = wholePart;
        this.fractionalPart = fractionalPart;
    }

    @SuppressWarnings("unchecked")
    public static <R extends Object> Function<ModelType, R> getCastFunctionForType(Class<R> type,
            Class<?> ignoreClass) {
        // Direct cast to subclasses
        if (Byte.class.equals(type)) {
            return o -> (R) Byte.valueOf(Long.valueOf(((BaseNumeric) o).wholePart).byteValue());
        } else if (Short.class.equals(type)) {
            return o -> (R) Short.valueOf(Long.valueOf(((BaseNumeric) o).wholePart).shortValue());
        } else if (Integer.class.equals(type)) {
            return o -> (R) Integer.valueOf(Long.valueOf(((BaseNumeric) o).wholePart).intValue());
        } else if (Long.class.equals(type)) {
            return o -> (R) Long.valueOf(((BaseNumeric) o).wholePart);
        } else if (Double.class.equals(type)) {
            return o -> ((BaseNumeric) o).wholePart > 0
                    ? (R) Double.valueOf(((BaseNumeric) o).wholePart + ((BaseNumeric) o).fractionalPart)
                    : (R) Double.valueOf(((BaseNumeric) o).wholePart - ((BaseNumeric) o).fractionalPart);
        } else if (Date.class.equals(type)) {
            return o -> (R) new Date(((BaseNumeric) o).wholePart);
        }

        // Attempt to find cast function in subclasses
        if (!Date.class.equals(ignoreClass)) {
            Function<ModelType, R> f = Date.getCastFunctionForType(type, Numeric.class);
            if (f != null) {
                return f;
            }
        }

        // Didn't find any applicable cast functions
        return null;
    }

    @Override
    public <R extends Object> Function<ModelType, R> getCastFunction(Class<R> type) {
        return BaseNumeric.getCastFunctionForType(type, null);
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