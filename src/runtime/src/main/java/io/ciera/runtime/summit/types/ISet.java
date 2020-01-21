package io.ciera.runtime.summit.types;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import io.ciera.runtime.summit.exceptions.XtumlException;

public interface ISet<E> extends Set<E>, IXtumlType {

    public ISet<E> union(ISet<E> set);

    public ISet<E> union(E element);

    public ISet<E> intersection(ISet<E> set);

    public ISet<E> intersection(E element);

    public ISet<E> difference(ISet<E> set);

    public ISet<E> difference(E element);

    public ISet<E> disunion(ISet<E> set);

    public ISet<E> disunion(E element);

    public E any();

    public ISet<E> where(IWhere<E> condition) throws XtumlException;

    public E anyWhere(IWhere<E> condition) throws XtumlException;

    public ISet<E> sorted(Comparator<E> comp) throws XtumlException;

    public ISet<E> sorted(Comparator<E> comp, boolean ascending) throws XtumlException;

    public E nullElement();

    public ISet<E> emptySet();

    public ISet<E> emptySet(Comparator<? super E> comp);

    public List<E> elements();

}
