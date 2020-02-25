package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

import java.util.function.BinaryOperator;

public class IntegerUDT implements IXtumlType, Comparable<IntegerUDT> {

    protected int value;

    public IntegerUDT() {
        value = 0;
    }

    public IntegerUDT(Object value) throws XtumlException {
        if (value instanceof Integer) {
            this.value = (int)value;
        }
        else if (value instanceof Double) {
            this.value = (int)((double)value);
        }
        else {
            throw new XtumlException("Could not promote type.");
        }
 
    }

    public int castInteger() {
        return value;
    }

    public boolean lessThan(IntegerUDT o) throws XtumlException {
        if (o != null) {
            return value < o.value;
        }
        throw new XtumlException("Bad arguments");
    }

    public boolean lessThanOrEqual(IntegerUDT o) throws XtumlException {
        if (o != null) {
            return value <= o.value;
        }
        throw new XtumlException("Bad arguments");
    }

    public boolean greaterThan(IntegerUDT o) throws XtumlException {
        if (o != null) {
            return value > o.value;
        }
        throw new XtumlException("Bad arguments");
    }

    public boolean greaterThanOrEqual(IntegerUDT o) throws XtumlException {
        if (o != null) {
            return value >= o.value;
        }
        throw new XtumlException("Bad arguments");
    }

    public <T extends IntegerUDT> T add(T o) throws XtumlException {
        return binop(o, (a, b) -> a + b);
    }

    public <T extends IntegerUDT> T subtract(T o) throws XtumlException {
        return binop(o, (a, b) -> a - b);
    }

    public <T extends IntegerUDT> T multiply(T o) throws XtumlException {
        return binop(o, (a, b) -> a * b);
    }

    public <T extends IntegerUDT> T divide(T o) throws XtumlException {
        return binop(o, (a, b) -> a / b);
    }

    public <T extends IntegerUDT> T remainder(T o) throws XtumlException {
        return binop(o, (a, b) -> a % b);
    }

    @SuppressWarnings("unchecked")
    private <T extends IntegerUDT> T binop(T o, BinaryOperator<Integer> op) throws XtumlException {
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
    public int compareTo(IntegerUDT o) {
        if (o != null) {
            return value - o.value;
        }
        return 0;
    }

    @Override
    public String serialize() throws XtumlException {
        return toString();
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Integer) {
            return value == ((int)o);
        }
        else {
            return o instanceof IntegerUDT && value == ((IntegerUDT)o).value;
        }
    }

}
