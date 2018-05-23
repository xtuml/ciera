package io.ciera.summit.types;

import java.util.Set;

import io.ciera.summit.exceptions.XtumlException;

public interface ISet<S extends ISet<S,E>, E> extends Set<E>, IXtumlType<S> {

    public S union( S set );
    public S intersection( S set );
    public S difference( S set );
    public S disunion( S set );

    public E any();
    public S where( IWhere<E> condition ) throws XtumlException;
    public E anyWhere( IWhere<E> condition ) throws XtumlException;

    public void setImmutable();
    public S toImmutableSet();
    public E nullElement();
    public S emptySet();

}
