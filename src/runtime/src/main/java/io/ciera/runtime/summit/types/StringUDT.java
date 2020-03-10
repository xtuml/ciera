package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public abstract class StringUDT extends UDT<String> {

    public StringUDT() {
        super("");
    }

    public StringUDT(Object o) throws XtumlException {
        super(castString(o));
    }

    public <T extends StringUDT> T add(Object o) throws XtumlException {
        return binop(o, (a, b) -> a + b);
    }

    @Override
    public String cast(Object o) throws XtumlException {
        return castString(o);
    }

    public static String castString(Object o) throws XtumlException {
        if (o instanceof String) {
            return (String)o;
        }
        else if (o instanceof StringUDT) {
            return ((StringUDT)o).cast();
        }
        else {
            throw new XtumlException("Could not promote type.");
        }
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof StringUDT) {
            return compareTo(((StringUDT)o).cast());
        }
        else if (o instanceof String) {
            return cast().compareTo((String)o);
        }
        return 0;
    }

    @Override
    public String toString() {
        return cast().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof String) {
            return cast().equals(o);
        }
        else {
            return o instanceof StringUDT && cast().equals(((StringUDT)o).cast());
        }
    }

}
