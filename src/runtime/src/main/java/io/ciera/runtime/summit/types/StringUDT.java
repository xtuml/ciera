package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

import java.util.function.BinaryOperator;

public class StringUDT implements IXtumlType, Comparable<StringUDT> {

    protected String value;

    public StringUDT() {
        value = "";
    }

    public StringUDT(Object value) throws XtumlException {
        if (value instanceof String) {
            this.value = (String)value;
        }
        else {
            throw new XtumlException("Could not promote type.");
        }
 
    }

    public String castString() {
        return value;
    }

    public boolean lessThan(StringUDT o) throws XtumlException {
        if (o != null) {
            return StringUtil.lessThan(value, o.value);
        }
        throw new XtumlException("Bad arguments");
    }

    public boolean lessThanOrEqual(StringUDT o) throws XtumlException {
        if (o != null) {
            return StringUtil.lessThanOrEqual(value, o.value);
        }
        throw new XtumlException("Bad arguments");
    }

    public boolean greaterThan(StringUDT o) throws XtumlException {
        if (o != null) {
            return StringUtil.greaterThan(value, o.value);
        }
        throw new XtumlException("Bad arguments");
    }

    public boolean greaterThanOrEqual(StringUDT o) throws XtumlException {
        if (o != null) {
            return StringUtil.greaterThanOrEqual(value, o.value);
        }
        throw new XtumlException("Bad arguments");
    }

    public <T extends StringUDT> T add(T o) throws XtumlException {
        return binop(o, (a, b) -> a + b);
    }

    @SuppressWarnings("unchecked")
    private <T extends StringUDT> T binop(T o, BinaryOperator<String> op) throws XtumlException {
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
    public int compareTo(StringUDT o) {
        if (o != null) {
            return value.compareTo(o.value);
        }
        return 0;
    }

    @Override
    public String serialize() throws XtumlException {
        return toString();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof String) {
            return value.equals(o);
        }
        else {
            return o instanceof StringUDT && value.equals(((StringUDT)o).value);
        }
    }

}
