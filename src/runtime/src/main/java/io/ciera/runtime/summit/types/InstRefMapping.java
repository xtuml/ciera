package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public abstract class InstRefMapping extends UDT<Object> {

    public InstRefMapping() {
        super("");
    }

    public InstRefMapping(Object o) throws XtumlException {
        super(o);
    }

    @Override
    public Object cast(Object o) throws XtumlException {
        return o;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof InstRefMapping) {
            return compareTo(((InstRefMapping)o).cast());
        }
        else if (o != null) {
            return cast().hashCode() - o.hashCode();
        }
        return 0;
    }

    @Override
    public String toString() {
        return cast().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof InstRefMapping) {
            return cast().equals(((InstRefMapping)o).cast());
        }
        else {
            return o != null && cast().equals(o);
        }
    }

}
