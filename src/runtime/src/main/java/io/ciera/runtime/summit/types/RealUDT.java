package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public abstract class RealUDT extends UDT<Double> {

    public RealUDT() {
        super(0d);
    }

    public RealUDT(Object o) throws XtumlException {
        super(castReal(o));
    }

    public <T extends RealUDT> T add(Object o) throws XtumlException {
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

    @Override
    public Double cast(Object o) throws XtumlException {
        return castReal(o);
    }

    public static Double castReal(Object o) throws XtumlException {
        if (o instanceof Double) {
            return (Double)o;
        }
        else if (o instanceof RealUDT) {
            return ((RealUDT)o).cast();
        }
        else {
            throw new XtumlException("Could not promote type.");
        }
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof RealUDT) {
            return compareTo(((RealUDT)o).cast());
        }
        else if (o instanceof Double) {
            return cast().compareTo((Double)o);
        }
        return 0;
    }

    @Override
    public String toString() {
        return cast().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Double) {
            return cast().equals(o);
        }
        else {
            return o instanceof RealUDT && cast().equals(((RealUDT)o).cast());
        }
    }

}
