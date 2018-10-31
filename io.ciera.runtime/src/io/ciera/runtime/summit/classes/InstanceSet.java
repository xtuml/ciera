package io.ciera.runtime.summit.classes;

import java.util.HashMap;
import java.util.Map;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.Set;
import io.ciera.runtime.summit.types.UniqueId;

public abstract class InstanceSet<S extends IInstanceSet<S,E>,E extends IModelInstance<E,?>> extends Set<S,E> implements IInstanceSet<S,E> {
    
    private Map<UniqueId, E> instanceIdMap;
    private Map<IInstanceIdentifier, E> id1Map;
    private Map<IInstanceIdentifier, E> id2Map;
    private Map<IInstanceIdentifier, E> id3Map;
    
    public InstanceSet() {
        super();
        instanceIdMap = null;
        id1Map = null;
        id2Map = null;
        id3Map = null;
    }

    @Override
    public boolean add( E e ) {
        if ( super.add(e) ) {
            if ( null != instanceIdMap ) instanceIdMap.put(e.getInstanceId(), e);
            if ( null != id1Map ) id1Map.put(e.getId1(), e);
            if ( null != id2Map ) id2Map.put(e.getId2(), e);
            if ( null != id3Map ) id3Map.put(e.getId3(), e);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove( Object o ) {
        if ( super.remove(o) ) {
            if ( null != instanceIdMap ) instanceIdMap.remove(((IModelInstance<?,?>)o).getInstanceId());
            if ( null != id1Map ) id1Map.remove(((IModelInstance<?,?>)o).getId1());
            if ( null != id2Map ) id2Map.remove(((IModelInstance<?,?>)o).getId2());
            if ( null != id3Map ) id3Map.remove(((IModelInstance<?,?>)o).getId3());
            return true;
        }
        return false;
    }

    @Override
    public E getByInstanceId( UniqueId instanceId ) throws XtumlException {
        populateInstanceIdMap();
        E inst = instanceIdMap.get(instanceId);
        return null != inst ? inst : nullElement();
    }

    @Override
    public E getById1( IInstanceIdentifier id1 ) throws XtumlException {
        populateId1Map();
        E inst = id1Map.get(id1);
        return null != inst ? inst : nullElement();
    }

    @Override
    public E getById2( IInstanceIdentifier id2 ) throws XtumlException {
        populateId2Map();
        E inst = id2Map.get(id2);
        return null != inst ? inst : nullElement();
    }

    @Override
    public E getById3( IInstanceIdentifier id3 ) throws XtumlException {
        populateId3Map();
        E inst = id3Map.get(id3);
        return null != inst ? inst : nullElement();
    }
    
    private void populateInstanceIdMap() {
        if ( null == instanceIdMap ) {
            instanceIdMap = new HashMap<>();
            for ( E e : this ) instanceIdMap.put(e.getInstanceId(), e);
        }
    }

    private void populateId1Map() {
        if ( null == id1Map ) {
            id1Map = new HashMap<>();
            for ( E e : this ) id1Map.put(e.getId1(), e);
        }
    }

    private void populateId2Map() {
        if ( null == id2Map ) {
            id2Map = new HashMap<>();
            for ( E e : this ) id2Map.put(e.getId2(), e);
        }
    }

    private void populateId3Map() {
        if ( null == id3Map ) {
            id3Map = new HashMap<>();
            for ( E e : this ) id3Map.put(e.getId3(), e);
        }
    }

}
