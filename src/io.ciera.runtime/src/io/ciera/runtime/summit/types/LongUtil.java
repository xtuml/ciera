package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public class LongUtil {
	
	public static long deserialize(Object o) throws XtumlException {
		if (o instanceof Long) {
			return (long)o;
		}
		else if (o instanceof Number) {
			return ((Number)o).longValue();
		}
		else if (o instanceof String) {
			return Long.parseLong((String)o);
		}
		else throw new XtumlException("Cannot deserialize long integer value");
	}
}
