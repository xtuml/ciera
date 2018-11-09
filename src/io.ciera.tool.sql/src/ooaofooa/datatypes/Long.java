package ooaofooa.datatypes;

import io.ciera.runtime.summit.types.IXtumlType;

public class Long extends Object implements IXtumlType<Long> {

    private long value;

    public Long() {
        value = 0l;
    }

    public Long(Object o) {
        if (o instanceof Integer) {
            value = ((Integer) o).longValue();
        }
    }

    @Override
    public Long value() {
        return this;
    }

    public long getValue() {
        return value;
    }
    
    public static Long deserialize(Object o) {
    	return new Long(o);
    }

}
