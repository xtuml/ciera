package com.cierasystems.cairn.classes;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.cierasystems.summit.application.Application;
import com.cierasystems.summit.classes.IEmptyInstance;
import com.cierasystems.summit.classes.IInstanceSet;
import com.cierasystems.summit.classes.IModelInstance;
import com.cierasystems.summit.classes.IWhere;

public abstract class InstanceSet implements IInstanceSet {
    
    private static Class<?> setClass = DefaultInstanceSet.class;

    private Set<IModelInstance> backingSet;
    private Class<?> type;
    
    public static <T extends Set<IModelInstance>> void setBackingSetClass( Class<T> newSetClass ) {
        setClass = newSetClass;
    }
    
    @SuppressWarnings("unchecked")
    public InstanceSet( Class<?> type ) {
        try {
            backingSet = (Set<IModelInstance>) setClass.newInstance();
        } catch ( Exception e ) {
            e.printStackTrace();
            Application.app.stop();
        }
        this.type = type;
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
        IInstanceSet return_set = getNewInstanceSetForClass( type );
        for ( IModelInstance selected : this ) {
            if ( null == condition || condition.evaluate(selected) ) {
                return_set.add(selected);
            }
        }
        return return_set;
    }
    
    @Override
    public IInstanceSet getNewInstanceSetForClass( Class<?> object ) {
        try {
            Method getSetClass = object.getMethod( "getSetClass" );
            Class<?> setClass = (Class<?>) getSetClass.invoke( null );
            Object newInstanceSet = setClass.newInstance();
            return (InstanceSet)newInstanceSet;
        }
        catch ( Exception e ) {
            e.printStackTrace();
            Application.app.stop();
        }
        return null;
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
        return backingSet.addAll(c);
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
