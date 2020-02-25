package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

import java.util.function.BinaryOperator;

public class RealUDT implements IXtumlType, Comparable<RealUDT> {

    protected double value;

    public RealUDT() {
        value = 0.0;
    }

    public RealUDT(Object value) throws XtumlException {
        if (value instanceof Double) {
            this.value = (double)value;
        }
        else {
            throw new XtumlException("Could not promote type.");
        }
 
    }

    public double castDouble() {
        return value;
    }

    public int castInteger() {
        return (int)value;
    }

    public boolean lessThan(RealUDT o) throws XtumlException {
        if (o != null) {
            return value < o.value;
        }
        throw new XtumlException("Bad arguments");
    }

    public boolean lessThanOrEqual(RealUDT o) throws XtumlException {
        if (o != null) {
            return value <= o.value;
        }
        throw new XtumlException("Bad arguments");
    }

    public boolean greaterThan(RealUDT o) throws XtumlException {
        if (o != null) {
            return value > o.value;
        }
        throw new XtumlException("Bad arguments");
    }

    public boolean greaterThanOrEqual(RealUDT o) throws XtumlException {
        if (o != null) {
            return value >= o.value;
        }
        throw new XtumlException("Bad arguments");
    }

    public <T extends RealUDT> T add(T o) throws XtumlException {
        return binop(o, (a, b) -> a + b);
    }

    public <T extends RealUDT> T subtract(T o) throws XtumlException {
        return binop(o, (a, b) -> a - b);
    }

    public <T extends RealUDT> T multiply(T o) throws XtumlException {
        return binop(o, (a, b) -> a * b);
    }

    public <T extends RealUDT> T divide(T o) throws XtumlException {
        return binop(o, (a, b) -> a / b);
    }

    public <T extends RealUDT> T remainder(T o) throws XtumlException {
        return binop(o, (a, b) -> a % b);
    }

    @SuppressWarnings("unchecked")
    private <T extends RealUDT> T binop(T o, BinaryOperator<Double> op) throws XtumlException {
        if (o != null) {
            try {
                return ((Class<T>)o.getClass()).getConstructor(Object.class).newInstance(op.apply(value, o.value));
            }
            catch (Exception e) {
                throw new XtumlException("Instance initialization error", e);
            }
        }
        throw new XtumlException("Bad arguments");
    }

    @Override
    public int compareTo(RealUDT o) {
        if (o != null) {
            return Double.compare(value, o.value);
        }
        return 0;
    }

    @Override
    public String serialize() throws XtumlException {
        return toString();
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Double) {
            return value == ((double)o);
        }
        else {
            return o instanceof RealUDT && value == ((RealUDT)o).value;
        }
    }

}
