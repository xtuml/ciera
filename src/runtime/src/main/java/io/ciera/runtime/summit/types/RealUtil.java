package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public class RealUtil {
    
    public static String serialize(double d) {
        return Double.toString(d);
    }
	
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

    public static int compareTo(double a, double b) {
        return Double.compare(a, b);
    }
}
