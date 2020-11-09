package ooaofooa.datatypes;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.IXtumlType;
import io.ciera.runtime.summit.types.InstRefMapping;
import io.ciera.runtime.summit.types.UDT;

public class ReentrantLock extends InstRefMapping implements IXtumlType {

    private Object obj;

    public ReentrantLock() {
        obj = null;
    }

    public ReentrantLock(Object o) {
        obj = o;
    }

    public Object getObj() {
        return obj;
    }

    public static ReentrantLock deserialize(Object o) {
    	return new ReentrantLock(o);
    }

    @SuppressWarnings("unchecked")
    public ReentrantLock promote(Object o) throws XtumlException {
        return new ReentrantLock(cast(o));
    }

}
