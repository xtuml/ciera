package io.ciera.cairn.classes;

import java.util.HashMap;
import java.util.Map;

import io.ciera.cairn.types.Set;
import io.ciera.summit.classes.IEmptyInstance;
import io.ciera.summit.classes.IInstanceIdentifier;
import io.ciera.summit.classes.IInstanceSet;
import io.ciera.summit.classes.IModelInstance;
import io.ciera.summit.classes.UniqueId;

public abstract class InstanceSet<S extends IInstanceSet<S,E>,E extends IModelInstance> extends Set<S,E> implements IInstanceSet<S,E> {

	private Map<UniqueId, E> instanceIdSet;
	private Map<IInstanceIdentifier, E> id1Set;
	private Map<IInstanceIdentifier, E> id2Set;
	private Map<IInstanceIdentifier, E> id3Set;
	
	public InstanceSet() {
		instanceIdSet = new HashMap<>();
		id1Set = new HashMap<>();
		id2Set = new HashMap<>();
		id3Set = new HashMap<>();
	}

	@Override
	public boolean add( E e ) {
		if ( null != e && !(e instanceof IEmptyInstance) && super.add( e ) ) {
			instanceIdSet.put( e.getInstanceId(), e );
			if ( null != e.getId1() ) id1Set.put( e.getId1(), e );
			if ( null != e.getId2() ) id2Set.put( e.getId2(), e );
			if ( null != e.getId3() ) id3Set.put( e.getId3(), e );
			return true;
		}
		else return false;
	}

	@Override
	public boolean remove( Object o ) {
		if ( null != o && !(o instanceof IEmptyInstance) && super.remove( o ) ) {
			instanceIdSet.remove( ((IModelInstance)o).getInstanceId() );
			if ( null != ((IModelInstance)o).getId1() ) id1Set.remove( ((IModelInstance)o).getId1() );
			if ( null != ((IModelInstance)o).getId2() )id2Set.remove( ((IModelInstance)o).getId2() );
			if ( null != ((IModelInstance)o).getId3() )id3Set.remove( ((IModelInstance)o).getId3() );
			return true;
		}
		else return false;
	}

	@Override
	public void clear() {
		super.clear();
		instanceIdSet.clear();
		id1Set.clear();
		id2Set.clear();
		id3Set.clear();
	}

	@Override
	public E getByInstanceId( UniqueId instanceId ) {
		return instanceIdSet.get( instanceId );
	}

	@Override
	public E getById1( IInstanceIdentifier id1 ) {
		return id1Set.get( id1 );
	}

	@Override
	public E getById2( IInstanceIdentifier id2 ) {
		return id1Set.get( id2 );
	}

	@Override
	public E getById3( IInstanceIdentifier id3 ) {
		return id1Set.get( id3 );
	}
	
}