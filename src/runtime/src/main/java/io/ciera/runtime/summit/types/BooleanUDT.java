package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public abstract class BooleanUDT extends UDT<Boolean> {

    public BooleanUDT() {
        super(false);
    }

    public BooleanUDT(Object o) throws XtumlException {
        super(castBoolean(o));
    }

    public boolean or(Object o) throws XtumlException {
        return cast() || cast(o);
    }

    public boolean and(Object o) throws XtumlException {
        return cast() && cast(o);
    }

    @Override
    public Boolean cast(Object o) throws XtumlException {
        return castBoolean(o);
    }

    private static Boolean castBoolean(Object o) throws XtumlException {
        if (o instanceof Boolean) {
            return (Boolean)o;
        }
        else if (o instanceof BooleanUDT) {
            return ((BooleanUDT)o).cast();
        }
        else {
            throw new XtumlException("Could not promote type.");
        }
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof BooleanUDT) {
            return compareTo(((BooleanUDT)o).cast());
        }
        else if (o instanceof Boolean) {
            return cast().compareTo((Boolean)o);
        }
        return 0;
    }

    @Override
    public String toString() {
        return cast().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Boolean) {
            return cast().equals(o);
        }
        else {
            return o instanceof BooleanUDT && cast().equals(((BooleanUDT)o).cast());
        }
    }

}
