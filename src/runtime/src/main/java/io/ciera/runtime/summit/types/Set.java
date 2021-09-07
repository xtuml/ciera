package io.ciera.runtime.summit.types;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.XtumlException;

public abstract class Set<E> implements ISet<E> {

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
    public ISet<E> union(ISet<E> set) {
        ISet<E> returnSet = emptySet(internalSet.comparator());
        returnSet.addAll(this);
        returnSet.addAll(set);
        return returnSet;
    }

    @Override
    public ISet<E> union(E element) {
        ISet<E> returnSet = emptySet(internalSet.comparator());
        returnSet.addAll(this);
        returnSet.add(element);
        return returnSet;
    }

    @Override
    public ISet<E> intersection(ISet<E> set) {
        ISet<E> returnSet = emptySet(internalSet.comparator());
        returnSet.addAll(this);
        returnSet.retainAll(set);
        return returnSet;
    }

    @Override
    public ISet<E> intersection(E element) {
        ISet<E> returnSet = emptySet(internalSet.comparator());
        if (this.contains(element))
            returnSet.add(element);
        return returnSet;
    }

    @Override
    public ISet<E> difference(ISet<E> set) {
        ISet<E> returnSet = emptySet(internalSet.comparator());
        returnSet.addAll(this);
        returnSet.removeAll(set);
        return returnSet;
    }

    @Override
    public ISet<E> difference(E element) {
        ISet<E> returnSet = emptySet(internalSet.comparator());
        returnSet.addAll(this);
        returnSet.remove(element);
        return returnSet;
    }

    @Override
    public ISet<E> disunion(ISet<E> set) {
        ISet<E> returnSet = emptySet(internalSet.comparator());
        returnSet.addAll(this);
        returnSet.addAll(set);
        ISet<E> returnSet2 = emptySet(internalSet.comparator());
        returnSet2.addAll(this);
        returnSet2.retainAll(set);
        returnSet.removeAll(returnSet2);
        return returnSet;
    }

    @Override
    public ISet<E> disunion(E element) {
        ISet<E> returnSet = emptySet(internalSet.comparator());
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
    public ISet<E> where(IWhere<E> condition) throws XtumlException {
        if (null == condition)
            throw new BadArgumentException("Null condition passed to selection.");
        ISet<E> resultSet = emptySet(internalSet.comparator());
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
    public ISet<E> sorted(Comparator<E> comp) throws XtumlException {
        return sorted(comp, true);
    }

    @Override
    public ISet<E> sorted(Comparator<E> comp, boolean ascending) throws XtumlException {
        ISet<E> returnSet = emptySet(ascending ? comp : (a, b) -> comp.compare(a, b) * -1);
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
        List<E> toRemove = newArrayList();
        for (E e : this) {
            if (!c.contains(e) && this.contains(e)) {
            	toRemove.add(e);
                retainAll = true;
            }
        }
        internalSet.removeAll(toRemove);
        return retainAll;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removeAll = false;
        for (Object o : c) {
            if (this.contains(o))
            	removeAll = true;
        }
        internalSet.removeAll(c);
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
    @SuppressWarnings("unchecked")
    public boolean equality(IXtumlType value) throws XtumlException {
    	if (value instanceof ISet<?>) {
    		return containsAll((ISet<E>)value) && ((ISet<E>)value).containsAll(this);
    	}
    	else return false;
    }

}
