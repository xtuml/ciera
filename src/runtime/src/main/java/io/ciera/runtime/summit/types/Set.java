package io.ciera.runtime.summit.types;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.XtumlException;

public abstract class Set<S extends ISet<S, E>, E> implements ISet<S, E> {

    private java.util.SortedSet<E> internalSet;

    public Set() {
        internalSet = new TreeSet<>();
    }

    public Set(Comparator<? super E> comp) {
        if (null == comp) {
            internalSet = new TreeSet<>();
        }
        else {
            internalSet = new TreeSet<>(comp);
        }
    }
    
    public Set(Collection<E> c) {
    	this();
    	addAll(c);
    }

    @Override
    public S union(S set) {
        S returnSet = emptySet(internalSet.comparator());
        returnSet.addAll(this);
        returnSet.addAll(set);
        return returnSet;
    }

    @Override
    public S union(E element) {
        S returnSet = emptySet(internalSet.comparator());
        returnSet.addAll(this);
        returnSet.add(element);
        return returnSet;
    }

    @Override
    public S intersection(S set) {
        S returnSet = emptySet(internalSet.comparator());
        returnSet.addAll(this);
        returnSet.retainAll(set);
        return returnSet;
    }

    @Override
    public S intersection(E element) {
        S returnSet = emptySet(internalSet.comparator());
        if (this.contains(element))
            returnSet.add(element);
        return returnSet;
    }

    @Override
    public S difference(S set) {
        S returnSet = emptySet(internalSet.comparator());
        returnSet.addAll(this);
        returnSet.removeAll(set);
        return returnSet;
    }

    @Override
    public S difference(E element) {
        S returnSet = emptySet(internalSet.comparator());
        returnSet.addAll(this);
        returnSet.remove(element);
        return returnSet;
    }

    @Override
    public S disunion(S set) {
        S returnSet = emptySet(internalSet.comparator());
        returnSet.addAll(this);
        returnSet.addAll(set);
        S returnSet2 = emptySet(internalSet.comparator());
        returnSet2.addAll(this);
        returnSet2.retainAll(set);
        returnSet.removeAll(returnSet2);
        return returnSet;
    }

    @Override
    public S disunion(E element) {
        S returnSet = emptySet(internalSet.comparator());
        returnSet.addAll(this);
        returnSet.add(element);
        if (this.contains(element))
            returnSet.remove(element);
        return returnSet;
    }

    @Override
    public E any() {
        if (!isEmpty())
            return iterator().next();
        else
            return nullElement();
    }

    @Override
    public S where(IWhere<E> condition) throws XtumlException {
        if (null == condition)
            throw new BadArgumentException("Null condition passed to selection.");
        S resultSet = emptySet(internalSet.comparator());
        for (E selected : this) {
            if (condition.evaluate(selected))
                resultSet.add(selected);
        }
        return resultSet;
    }

    @Override
    public E anyWhere(IWhere<E> condition) throws XtumlException {
        if (null == condition)
            throw new BadArgumentException("Null condition passed to selection.");
        for (E selected : this) {
            if (condition.evaluate(selected))
                return selected;
        }
        return nullElement();
    }

    @Override
    public S sorted(Comparator<E> comp) throws XtumlException {
        return sorted(comp, true);
    }

    @Override
    public S sorted(Comparator<E> comp, boolean ascending) throws XtumlException {
        S returnSet = emptySet(ascending ? comp : (a, b) -> comp.compare(a, b) * -1);
        returnSet.addAll(elements());
        return returnSet;
    }

    @Override
    public boolean add(E e) {
        if (null == e || e.equals(nullElement()))
            return false;
        else
            return internalSet.add(e);
    }

    @Override
    public boolean remove(Object o) {
        if (null == o || o.equals(nullElement()))
            return false;
        else
            return internalSet.remove(o);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean addAll = false;
        for (E e : c) {
            addAll = add(e) || addAll;
        }
        return addAll;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean retainAll = false;
        for (E e : this) {
            if (!c.contains(e)) {
                retainAll = remove(e) || retainAll;
            }
        }
        return retainAll;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removeAll = false;
        for (Object o : c) {
            removeAll = remove(o) || removeAll;
        }
        return removeAll;
    }

    // Methods inherited from HashSet
    @Override
    public int size() {
        return internalSet.size();
    }

    @Override
    public boolean isEmpty() {
        return internalSet.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return internalSet.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return internalSet.iterator();
    }

    @Override
    public Object[] toArray() {
        return internalSet.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return internalSet.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return internalSet.containsAll(c);
    }

    @Override
    public void clear() {
        internalSet.clear();
    }

    @Override
    public boolean equality(S value) throws XtumlException {
        return containsAll(value) && value.containsAll(this);
    }

}
