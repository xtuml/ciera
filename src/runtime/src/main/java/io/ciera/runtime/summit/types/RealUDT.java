package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public class RealUDT implements IXtumlType {

    private double value;

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

    @Override
    public String serialize() throws XtumlException {
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
