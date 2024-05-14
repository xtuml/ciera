package io.ciera.runtime.summit.types;

import java.util.function.Function;

import org.json.JSONArray;

import io.ciera.runtime.summit.exceptions.NotImplementedException;
import io.ciera.runtime.summit.exceptions.XtumlException;

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
    
    public static String serialize(Object[] o) throws XtumlException {
        final JSONArray json = new JSONArray();
        for (Object elem : o) {
            if (elem instanceof IXtumlType) {
                json.put(((IXtumlType) elem).serialize());
            } else {
                json.put(o.toString());
            }
        }
        return json.toString();
    }

    public static Object[] deserialize(Object o) throws XtumlException {
        if (o instanceof String) {
            return new JSONArray((String) o).toList().toArray();
        } else {
            throw new NotImplementedException("Array cannot be deserialized");
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] deserialize(Object o, Function<Object, T> elementDeserializer) throws XtumlException {
        if (o instanceof String) {
            return (T[]) new JSONArray((String) o).toList().stream().map(elementDeserializer).toArray();
        } else {
            throw new NotImplementedException("Array cannot be deserialized");
        }
    }

}
