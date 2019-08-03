package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public class RealUtil {
	
	public static double deserialize(Object o) throws XtumlException {
		if (o instanceof Double) {
			return (double)o;
		}
		else if (o instanceof Number) {
			return ((Number)o).doubleValue();
		}
		else if (o instanceof String) {
			return Double.parseDouble((String)o);
		}
		else throw new XtumlException("Cannot deserialize real value");
	}
}
