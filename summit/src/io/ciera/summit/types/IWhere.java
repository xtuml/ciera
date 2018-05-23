package io.ciera.summit.types;

import io.ciera.summit.exceptions.XtumlException;

public interface IWhere<E> {

    public boolean evaluate( E selected ) throws XtumlException;

}
