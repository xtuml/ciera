package io.ciera.runtime.summit.types;

import java.util.List;

import io.ciera.runtime.summit.exceptions.XtumlException;

public interface IList<E> extends List<E>, IXtumlType {

    public E any();

    public IList<E> where(IWhere<E> condition) throws XtumlException;

    public E anyWhere(IWhere<E> condition) throws XtumlException;

    public E nullElement();

    public IList<E> emptyList();

}
