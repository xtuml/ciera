package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public class IntegerUtil {
    
    public static String serialize(int i) {
        return Integer.toString(i);
    }
	
	public static int deserialize(Object o) throws XtumlException {
		if (o instanceof Integer) {
			return (int)o;
		}
		else if (o instanceof Number) {
			return ((Number)o).intValue();
		}
		else if (o instanceof String) {
			return Integer.parseInt((String)o);
		}
		else throw new XtumlException("Cannot deserialize integer value");
	}

    public static int compareTo(int a, int b) {
        return Integer.compare(a, b);
    }
}
