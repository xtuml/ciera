package io.ciera.summit.types;

import io.ciera.summit.exceptions.XtumlException;

public interface IXtumlType<T extends IXtumlType<T>> {

    public boolean equality( T value ) throws XtumlException;
    default public boolean inequality( T value ) throws XtumlException {
        return !equality( value );
    }
    public T defaultValue();

}
