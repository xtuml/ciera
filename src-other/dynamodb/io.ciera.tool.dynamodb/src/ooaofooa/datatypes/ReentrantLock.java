package ooaofooa.datatypes;

import io.ciera.runtime.summit.types.IXtumlType;
import io.ciera.runtime.summit.types.InstRefMapping;

public class ReentrantLock extends InstRefMapping<ReentrantLock> implements IXtumlType<ReentrantLock> {

    private Object obj;

    public ReentrantLock() {
        obj = null;
    }

    public ReentrantLock(Object o) {
        obj = o;
    }

    @Override
    public ReentrantLock value() {
        return this;
    }

    public Object getObj() {
        return obj;
    }

    public static ReentrantLock deserialize(Object o) {
    	return new ReentrantLock(o);
    }

}
