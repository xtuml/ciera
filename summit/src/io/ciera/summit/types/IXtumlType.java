package io.ciera.summit.types;

import io.ciera.summit.exceptions.XtumlException;

public interface IXtumlType<T extends IXtumlType<T>> {
	
	public boolean equality( IXtumlType<T> value ) throws XtumlException;
	public T defaultValue();

}
