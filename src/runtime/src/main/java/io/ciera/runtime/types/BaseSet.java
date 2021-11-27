package io.ciera.runtime.types;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;

public abstract class BaseSet<E> extends TreeSet<E> {

    private static final long serialVersionUID = 1l;

    public BaseSet() {
        super();
    }

    public BaseSet(Collection<? extends E> c) {
        super(c);
    }

    public BaseSet(Comparator<? super E> comparator) {
        super(comparator);
    }

    public BaseSet(SortedSet<E> s) {
        super(s);
    }
    
    public E any() {
        return any((o) -> true);
    }
    
    public abstract E any(Predicate<E> where);
    
    public abstract BaseSet<E> where(Predicate<E> where);

}
