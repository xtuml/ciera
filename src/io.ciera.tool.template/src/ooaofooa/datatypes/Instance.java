package ooaofooa.datatypes;

import io.ciera.runtime.summit.types.IXtumlType;
import io.ciera.runtime.summit.types.InstRefMapping;

public class Instance extends InstRefMapping<Instance> implements IXtumlType<Instance> {

    private Object obj;

    public Instance() {
        obj = null;
    }

    public Instance(Object o) {
        obj = o;
    }

    @Override
    public Instance value() {
        return this;
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

}
