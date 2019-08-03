package io.ciera.runtime.summit.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.XtumlException;

public abstract class List<L extends IList<L, E>, E> implements IList<L, E> {

    private ArrayList<E> internalList;

    public List() {
        internalList = new ArrayList<>();
    }

    public List(Collection<? extends E> c) {
        internalList = new ArrayList<>(c);
    }

    public List(int initialCapacity) {
        internalList = new ArrayList<>(initialCapacity);
    }

    @Override
    public E any() {
        if (!isEmpty())
            return iterator().next();
        else
            return nullElement();
    }

    @Override
    public L where(IWhere<E> condition) throws XtumlException {
        if (null == condition)
            throw new BadArgumentException("Null condition passed to selection.");
        L resultList = emptyList();
        for (E selected : this) {
            if (condition.evaluate(selected))
                resultList.add(selected);
        }
        return resultList;
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
    public E get(int index) {
        internalList.ensureCapacity(index + 1);
        return internalList.get(index);
    }

    @Override
    public E set(int index, E element) {
        internalList.ensureCapacity(index + 1);
        return internalList.set(index, element);
    }

    @Override
    public boolean add(E e) {
        if (null == e || e.equals(nullElement()))
            return false;
        else
            return internalList.add(e);
    }

    @Override
    public void add(int index, E element) {
        internalList.ensureCapacity(index + 1);
        internalList.add(index, element);
    }

    @Override
    public boolean remove(Object o) {
        if (null == o || o.equals(nullElement()))
            return false;
        else
            return internalList.remove(o);
    }

    @Override
    public E remove(int index) {
        return internalList.remove(index);
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

    // Methods inherited from ArrayList
    @Override
    public int size() {
        return internalList.size();
    }

    @Override
    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return internalList.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return internalList.iterator();
    }

    @Override
    public Object[] toArray() {
        return internalList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return internalList.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return internalList.containsAll(c);
    }

    @Override
    public void clear() {
        internalList.clear();
    }

    @Override
    public int indexOf(Object o) {
        return internalList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return internalList.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return internalList.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return internalList.listIterator(index);
    }

    @Override
    public java.util.List<E> subList(int fromIndex, int toIndex) {
        return internalList.subList(fromIndex, toIndex);
    }

    // XtumlType
    @Override
    public boolean equality(L value) throws XtumlException {
        return containsAll(value) && value.containsAll(this);
    }

}
