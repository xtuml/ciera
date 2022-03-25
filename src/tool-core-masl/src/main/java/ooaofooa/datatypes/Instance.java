package ooaofooa.datatypes;


import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.IXtumlType;
import io.ciera.runtime.summit.types.InstRefMapping;


public class Instance extends InstRefMapping implements IXtumlType {

    public Instance() {
        super();
    }

    public Instance(Object value) throws XtumlException {
        super(value);
    }

    @SuppressWarnings("unchecked")
    public Instance promote(Object o) throws XtumlException {
        return new Instance(cast(o));
    }

    public static Instance deserialize(Object o) throws XtumlException {
    	return new Instance(o);
    }

}
