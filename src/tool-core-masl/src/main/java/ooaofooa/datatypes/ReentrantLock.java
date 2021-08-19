package ooaofooa.datatypes;


import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.IXtumlType;
import io.ciera.runtime.summit.types.InstRefMapping;


public class ReentrantLock extends InstRefMapping implements IXtumlType {

    public ReentrantLock() {
        super();
    }

    public ReentrantLock(Object value) throws XtumlException {
        super(value);
    }

    @SuppressWarnings("unchecked")
    public ReentrantLock promote(Object o) throws XtumlException {
        return new ReentrantLock(cast(o));
    }

    public static ReentrantLock deserialize(Object o) throws XtumlException {
    	return new ReentrantLock(o);
    }

}
