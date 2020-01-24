package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public class BooleanUDT implements IXtumlType {

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

    @Override
    public String serialize() throws XtumlException {
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
