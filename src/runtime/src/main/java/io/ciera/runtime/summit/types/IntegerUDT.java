package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public class IntegerUDT implements IXtumlType {

    private int value;

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

    @Override
    public String serialize() throws XtumlException {
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
