package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.NotImplementedException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import java.util.Arrays;

public class ArrayUtil {

    public static boolean equality(Object[] value1, Object[] value2) throws XtumlException {
        if (null != value1 && null != value2 && value1.length == value2.length) {
            boolean equal = true;
            for (int i = 0; i < value1.length; i++) {
                if (value1[i] instanceof IXtumlType && value2[i] instanceof IXtumlType) {
                    equal = equal && ((IXtumlType)value1[i]).equality((IXtumlType)value2[i]);
                }
                else if (value1[i] instanceof IXtumlType && value2[i] instanceof IXtumlType) {
                    equal = equal && value1[i].equals(value2[i]);
                }
                else {
                    equal = false;
                }

            }
            return equal;
        }
        else {
            return false;
        }
    }

    public static boolean inequality(Object[] value1, Object[] value2) throws XtumlException {
        return !equality(value1, value2);
    }

    public static boolean greaterThan(Object[] value1, Object[] value2) throws XtumlException {
        throw new NotImplementedException("Array types are not comparable");
    }

    public static boolean lessThan(Object[] value1, Object[] value2) throws XtumlException {
        throw new NotImplementedException("Array types are not comparable");
    }

    public static boolean greaterThanOrEqual(Object[] value1, Object[] value2) throws XtumlException {
        throw new NotImplementedException("Array types are not comparable");
    }

    public static boolean lessThanOrEqual(Object[] value1, Object[] value2) throws XtumlException {
        throw new NotImplementedException("Array types are not comparable");
    }

	public static Object[] deserialize(Object o) throws XtumlException {
        throw new NotImplementedException("Array cannot be deserialized");
	}

}
