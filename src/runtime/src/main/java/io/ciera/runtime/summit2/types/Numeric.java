package io.ciera.runtime.summit2.types;

/**
 * The Numeric interface provides an abstract specification for all numeric
 * types. Numeric types can participate in arithmetic operations and be compared
 * to one another.
 *
 * TODO document methods
 */
public interface Numeric extends Comparable<Object> {

    public Numeric add(Object o);

    public Numeric subtract(Object o);

    public Numeric multiply(Object o);

    public Numeric divide(Object o);

    public Numeric modulo(Object o);

    public Numeric remainder(Object o);

    public Numeric power(Object o);

    default public boolean lessThan(Object o) {
        return compareTo(o) < 0;
    }

    default public boolean lessThanOrEqual(Object o) {
        return compareTo(o) <= 0;
    }

    default public boolean greaterThan(Object o) {
        return compareTo(o) > 0;
    }

    default public boolean greaterThanOrEqual(Object o) {
        return compareTo(o) >= 0;
    }

}