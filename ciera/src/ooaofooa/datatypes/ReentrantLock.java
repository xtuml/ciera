package ooaofooa.datatypes;

import io.ciera.summit.types.IXtumlType;
import io.ciera.summit.types.InstRefMapping;

public class ReentrantLock extends InstRefMapping<ReentrantLock> implements IXtumlType<ReentrantLock> {
    
    private Object obj;
    
    public ReentrantLock() {
        obj = null;
    }
    
    public ReentrantLock( Object o ) {
        obj = o;
    }
    
    @Override
    public ReentrantLock value() {
        return this;
    }
    
    public Object getObj() {
        return obj;
    }
    
}
