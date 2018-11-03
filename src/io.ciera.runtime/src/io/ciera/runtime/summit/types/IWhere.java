package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public interface IWhere<E> {

    public boolean evaluate(E selected) throws XtumlException;

}
