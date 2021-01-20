package ooaofooa.datatypes;


import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.IXtumlType;
import io.ciera.runtime.summit.types.IntegerUDT;


public class Long extends IntegerUDT implements IXtumlType {

    public Long() {
        super();
    }

    public Long(Object value) throws XtumlException {
        super(value);
    }

    @SuppressWarnings("unchecked")
    public Long promote(Object o) throws XtumlException {
        return new Long(cast(o));
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
