package ciera.classes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import ciera.application.XtumlApplication;

public abstract class InstanceSet implements Set<ModelInstance> {
    
    private static Class<?> setClass = DefaultInstanceSet.class;
    private Set<ModelInstance> backingSet;
    
    public static <T extends Set<ModelInstance>> void setBackingSetClass( Class<T> newSetClass ) {
        setClass = newSetClass;
    }
    
    @SuppressWarnings("unchecked")
    public InstanceSet() {
        try {
            backingSet = (Set<ModelInstance>) setClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    public ModelInstance selectAny( Where condition ) {
        for ( ModelInstance selected : this ) {
            if ( null == condition || condition.evaluate(selected) ) {
                return selected;
            }
        }
        return getEmptyInstance();
    }
    
    public InstanceSet selectMany( Where condition ) {
        InstanceSet return_set = (InstanceSet)clone();
        for ( ModelInstance selected : this ) {
            if ( null != condition && !condition.evaluate(selected) ) {
                return_set.remove(selected);
            }
        }
        return return_set;
    }
    
    public static InstanceSet getNewInstanceSetForClass( Class<?> object ) {
        try {
            Field setField = object.getField( "setClass" );
            Class<?> setClass = (Class<?>) setField.get( null );
            Constructor<?> setConstructor = setClass.getConstructor();
            Object newInstanceSet = setConstructor.newInstance();
            return (InstanceSet)newInstanceSet;
        }
        catch ( Exception e ) {
            e.printStackTrace();
            XtumlApplication.app.stop();
        }
        return null;
    }

    public abstract ModelInstance getEmptyInstance();
    
    @Override
    public Object clone() {
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
    public Iterator<ModelInstance> iterator() {
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
    public boolean add(ModelInstance e) {
        return backingSet.add(e);
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
    public boolean addAll(Collection<? extends ModelInstance> c) {
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
class DefaultInstanceSet extends HashSet<ModelInstance> {
}