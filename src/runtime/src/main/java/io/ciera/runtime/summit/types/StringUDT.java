package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public class StringUDT implements IXtumlType {

    private String value;

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

    @Override
    public String serialize() throws XtumlException {
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
