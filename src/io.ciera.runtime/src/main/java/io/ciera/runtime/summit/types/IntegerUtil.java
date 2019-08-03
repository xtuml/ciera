package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public class IntegerUtil {
	
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
}
