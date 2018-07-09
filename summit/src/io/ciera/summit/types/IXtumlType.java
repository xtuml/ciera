package io.ciera.summit.types;

import io.ciera.summit.exceptions.XtumlException;

public interface IXtumlType<T extends IXtumlType<T>> {

    default public boolean equality( T value ) throws XtumlException {
        return equals( value );
    }

    default public boolean inequality( T value ) throws XtumlException {
        return !equality( value );
    }

    default public T defaultValue() {
        return null;
    }

}
