package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

import java.util.function.BinaryOperator;

public abstract class UDT<T> implements IXtumlType, Comparable<Object> {

    private T value;

    public UDT(T o) {
        value = o;
    }

    public abstract <U extends UDT<T>> U promote(Object o) throws XtumlException;

    public T cast() {
        return value;
    }

    public abstract T cast(Object o) throws XtumlException;

    public boolean lessThan(Object o) {
        return compareTo(o) < 0;
    }

    public boolean lessThanOrEqual(Object o) throws XtumlException {
        return compareTo(o) <= 0;
    }

    public boolean greaterThan(Object o) throws XtumlException {
        return compareTo(o) > 0;
    }

    public boolean greaterThanOrEqual(Object o) throws XtumlException {
        return compareTo(o) >= 0;
    }

    protected <U extends UDT<T>> U binop(Object o, BinaryOperator<T> op) throws XtumlException {
        return promote(op.apply(value, promote(o).cast()));
    }

    @Override
    public String serialize() throws XtumlException {
        return toString();
    }

}
