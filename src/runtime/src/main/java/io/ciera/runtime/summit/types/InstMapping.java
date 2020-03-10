package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public abstract class InstMapping extends UDT<Object> {

    public InstMapping() {
        super("");
    }

    public InstMapping(Object o) throws XtumlException {
        super(o);
    }

    @Override
    public Object cast(Object o) throws XtumlException {
        return o;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof InstMapping) {
            return compareTo(((InstMapping)o).cast());
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
        if (o instanceof InstMapping) {
            return cast().equals(((InstMapping)o).cast());
        }
        else {
            return o != null && cast().equals(o);
        }
    }

}
