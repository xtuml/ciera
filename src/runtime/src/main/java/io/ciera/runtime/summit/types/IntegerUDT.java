package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public abstract class IntegerUDT extends UDT<Integer> {

    public IntegerUDT() {
        super(0);
    }

    public IntegerUDT(Object o) throws XtumlException {
        super(castInteger(o));
    }

    public <T extends IntegerUDT> T add(Object o) throws XtumlException {
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

    @Override
    public Integer cast(Object o) throws XtumlException {
        return castInteger(o);
    }

    public static Integer castInteger(Object o) throws XtumlException {
        if (o instanceof Integer) {
            return (Integer)o;
        }
        else if (o instanceof Double) {
            return (int)((double)o);
        }
        else if (o instanceof IntegerUDT) {
            return ((IntegerUDT)o).cast();
        }
        else {
            throw new XtumlException("Could not promote type.");
        }
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof IntegerUDT) {
            return compareTo(((IntegerUDT)o).cast());
        }
        else if (o instanceof Integer) {
            return cast().compareTo((Integer)o);
        }
        return 0;
    }

    @Override
    public String toString() {
        return cast().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Integer) {
            return cast().equals(o);
        }
        else {
            return o instanceof IntegerUDT && cast().equals(((IntegerUDT)o).cast());
        }
    }

}
