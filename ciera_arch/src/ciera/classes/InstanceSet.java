package ciera.classes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import ciera.application.XtumlApplication;

public abstract class InstanceSet implements Set<ModelInstance> {
    
    private static Class<?> setClass = DefaultInstanceSet.class;

    private Set<ModelInstance> backingSet;
    private Class<?> type;
    
    public static <T extends Set<ModelInstance>> void setBackingSetClass( Class<T> newSetClass ) {
        setClass = newSetClass;
    }
    
    @SuppressWarnings("unchecked")
    public InstanceSet( Class<?> type ) {
        try {
            backingSet = (Set<ModelInstance>) setClass.newInstance();
        } catch ( Exception e ) {
            e.printStackTrace();
            XtumlApplication.app.stop();
        }
        this.type = type;
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
        InstanceSet return_set = getNewInstanceSetForClass( type );
        for ( ModelInstance selected : this ) {
            if ( null == condition || condition.evaluate(selected) ) {
                return_set.add(selected);
            }
        }
        return return_set;
    }
    
    public static InstanceSet getNewInstanceSetForClass( Class<?> object ) {
        try {
            Method getSetClass = object.getMethod( "getSetClass" );
            Class<?> setClass = (Class<?>) getSetClass.invoke( null );
            Constructor<?> constructor = setClass.getConstructor( Class.class );
            Object newInstanceSet = constructor.newInstance( object );
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
        if ( e instanceof EmptyInstance ) return false;
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
