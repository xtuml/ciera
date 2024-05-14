package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public class BooleanUtil {
    
    public static String serialize(boolean b) {
        return Boolean.toString(b);
    }
    
    public static boolean deserialize(Object o) throws XtumlException {
        if (o instanceof Boolean) {
            return (boolean)o;
        }
        else if (o instanceof Number) {
            return ((Number)o).intValue() == 0 ? false : true;
        }
        else if (o instanceof String) {
            return Boolean.parseBoolean((String)o);
        }
        else throw new XtumlException("Cannot deserialize boolean value");
    }

    public static int compareTo(boolean a, boolean b) {
        return Boolean.compare(a, b);
    }

}
