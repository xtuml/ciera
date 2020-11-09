package ooaofooa.datatypes;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.IXtumlType;
import io.ciera.runtime.summit.types.InstRefMapping;
import io.ciera.runtime.summit.types.UDT;

public class Instance extends InstRefMapping implements IXtumlType {

    private Object obj;

    public Instance() {
        obj = null;
    }

    public Instance(Object o) {
        obj = o;
    }
    
    public Object getObj() {
        return obj;
    }

    public String serialize() {
        if ("".equals(obj))
            return "";
        else
            return obj.toString();
    }

    public static Instance deserialize(Object o) {
    	return new Instance(o);
    }

    @SuppressWarnings("unchecked")
    public Instance promote(Object o) throws XtumlException {
        return new Instance(cast(o));
    }

}
