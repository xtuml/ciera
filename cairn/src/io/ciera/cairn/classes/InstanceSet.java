package io.ciera.cairn.classes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.ciera.cairn.types.Set;
import io.ciera.summit.classes.IInstanceIdentifier;
import io.ciera.summit.classes.IInstanceSet;
import io.ciera.summit.classes.IModelInstance;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.UniqueId;

public abstract class InstanceSet<S extends IInstanceSet<S,E>,E extends IModelInstance<E,?>> extends Set<S,E> implements IInstanceSet<S,E> {
    
    private Map<UniqueId, E> instanceIdMap;
    private Map<IInstanceIdentifier, E> id1Map;
    private Map<IInstanceIdentifier, E> id2Map;
    private Map<IInstanceIdentifier, E> id3Map;
    
    public InstanceSet() {
        super();
        instanceIdMap = new HashMap<>();
        id1Map = new HashMap<>();
        id2Map = new HashMap<>();
        id3Map = new HashMap<>();
    }

    public InstanceSet( Collection<? extends E> c ) {
        super(c);
        instanceIdMap = new HashMap<>();
        id1Map = new HashMap<>();
        id2Map = new HashMap<>();
        id3Map = new HashMap<>();
    }

    public InstanceSet( int initialCapacity ) {
        super(initialCapacity);
        instanceIdMap = new HashMap<>();
        id1Map = new HashMap<>();
        id2Map = new HashMap<>();
        id3Map = new HashMap<>();
    }

    public InstanceSet( int initialCapacity, float loadFactor ) {
        super(initialCapacity, loadFactor);
        instanceIdMap = new HashMap<>();
        id1Map = new HashMap<>();
        id2Map = new HashMap<>();
        id3Map = new HashMap<>();
    }
    
    @Override
    public boolean add( E e ) {
        if ( super.add(e) ) {
            instanceIdMap.put(e.getInstanceId(), e);
            id1Map.put(e.getId1(), e);
            id2Map.put(e.getId2(), e);
            id3Map.put(e.getId3(), e);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove( Object o ) {
        if ( super.remove(o) ) {
            instanceIdMap.remove(((IModelInstance<?,?>)o).getInstanceId());
            id1Map.remove(((IModelInstance<?,?>)o).getId1());
            id2Map.remove(((IModelInstance<?,?>)o).getId2());
            id3Map.remove(((IModelInstance<?,?>)o).getId3());
            return true;
        }
        return false;
    }

    @Override
    public E getByInstanceId( UniqueId instanceId ) throws XtumlException {
        E inst = instanceIdMap.get(instanceId);
        return null != inst ? inst : nullElement();
    }

    @Override
    public E getById1( IInstanceIdentifier id1 ) throws XtumlException {
        E inst = id1Map.get(id1);
        return null != inst ? inst : nullElement();
    }

    @Override
    public E getById2( IInstanceIdentifier id2 ) throws XtumlException {
        E inst = id2Map.get(id2);
        return null != inst ? inst : nullElement();
    }

    @Override
    public E getById3( IInstanceIdentifier id3 ) throws XtumlException {
        E inst = id3Map.get(id3);
        return null != inst ? inst : nullElement();
    }

}
