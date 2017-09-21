package com.cierasystems.cairn.classes;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.cierasystems.summit.classes.IEmptyInstance;
import com.cierasystems.summit.classes.IInstanceSet;
import com.cierasystems.summit.classes.IModelInstance;
import com.cierasystems.summit.classes.IWhere;

public abstract class InstanceSet implements IInstanceSet {
    
    private Set<IModelInstance> backingSet;
    
    public Set<IModelInstance> getNewBackingSet() {
    	return new DefaultInstanceSet();
    }
    
    public InstanceSet() {
        backingSet = getNewBackingSet();
    }
    
    @Override
    public IModelInstance selectAny( IWhere condition ) {
        for ( IModelInstance selected : this ) {
            if ( null == condition || condition.evaluate(selected) ) {
                return selected;
            }
        }
        return getEmptyInstance();
    }
    
    @Override
    public IInstanceSet selectMany( IWhere condition ) {
        IInstanceSet return_set = null;
        for ( IModelInstance selected : this ) {
            if ( null == condition || condition.evaluate(selected) ) {
            	if ( null == return_set ) return_set = selected.toSet();
            	else return_set.add(selected);
            }
        }
        return return_set;
    }
    
    @Override
    public int size() {
        return backingSet.size();
    }

    @Override
    public boolean isEmpty() {
        return backingSet.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return backingSet.contains(o);
    }

    @Override
    public Iterator<IModelInstance> iterator() {
        return backingSet.iterator();
    }

    @Override
    public Object[] toArray() {
        return backingSet.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return backingSet.toArray(a);
    }

    @Override
    public boolean add(IModelInstance e) {
        if ( e instanceof IEmptyInstance ) return false;
        else return backingSet.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return backingSet.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return backingSet.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends IModelInstance> c) {
    	boolean ret_val = false;
    	for ( IModelInstance instance : c ) {
    		ret_val = ret_val || add( instance );
    	}
        return ret_val;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return backingSet.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return backingSet.removeAll(c);
    }

    @Override
    public void clear() {
        backingSet.clear();
    }

}

@SuppressWarnings("serial")
class DefaultInstanceSet extends HashSet<IModelInstance> {
}
