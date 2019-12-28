package io.ciera.runtime.summit.types;

import java.util.List;

import io.ciera.runtime.summit.exceptions.XtumlException;

public interface IList<L extends IList<L, E>, E> extends List<E>, IXtumlType<L> {

    public E any();

    public L where(IWhere<E> condition) throws XtumlException;

    public E anyWhere(IWhere<E> condition) throws XtumlException;

    public E nullElement();

    public L emptyList();

}
