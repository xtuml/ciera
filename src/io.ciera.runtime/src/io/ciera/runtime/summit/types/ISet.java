package io.ciera.runtime.summit.types;

import java.util.List;
import java.util.Set;

import io.ciera.runtime.summit.exceptions.XtumlException;

public interface ISet<S extends ISet<S, E>, E> extends Set<E>, IXtumlType<S> {

    public S union(S set);

    public S union(E element);

    public S intersection(S set);

    public S intersection(E element);

    public S difference(S set);

    public S difference(E element);

    public S disunion(S set);

    public S disunion(E element);

    public E any();

    public S where(IWhere<E> condition) throws XtumlException;

    public E anyWhere(IWhere<E> condition) throws XtumlException;

    public E nullElement();

    public S emptySet();

    public List<E> elements();

}
