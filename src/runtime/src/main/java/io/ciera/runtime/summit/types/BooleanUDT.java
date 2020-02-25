package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public class BooleanUDT implements IXtumlType, Comparable<BooleanUDT> {

    private boolean value;

    public BooleanUDT() {
        value = false;
    }

    public BooleanUDT(Object value) throws XtumlException {
        if (value instanceof Boolean) {
            this.value = (boolean)value;
        }
        else {
            throw new XtumlException("Could not promote type.");
        }
 
    }

    public boolean castBoolean() {
        return value;
    }

    public boolean or(BooleanUDT o) throws XtumlException {
        if (o != null) {
            return value || o.value;
        }
        throw new XtumlException("Bad arguments");
    }

    public boolean and(BooleanUDT o) throws XtumlException {
        if (o != null) {
            return value && o.value;
        }
        throw new XtumlException("Bad arguments");
    }

    @Override
    public int compareTo(BooleanUDT o) {
        if (o != null) {
            return Boolean.compare(value, o.value);
        }
        return 0;
    }

    @Override
    public String serialize() throws XtumlException {
        return toString();
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Boolean) {
            return value == ((boolean)o);
        }
        else {
            return o instanceof BooleanUDT && value == ((BooleanUDT)o).value;
        }
    }

}
