package ooaofooa.datatypes;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.IXtumlType;

public class Long extends Object implements IXtumlType {

    private long value;

    public Long() {
        value = 0l;
    }

    public Long(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static Long deserialize(Object o) throws XtumlException {
        if (o instanceof Long) {
            return new Long((long)o);
        }
        else if (o instanceof Number) {
            return new Long(((Number)o).longValue());
        }
        else if (o instanceof String) {
            return new Long(java.lang.Long.parseLong((String)o));
        }
        else throw new XtumlException("Cannot deserialize integer value");
    }

}
