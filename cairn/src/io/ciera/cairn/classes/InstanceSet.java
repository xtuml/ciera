package io.ciera.cairn.classes;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import io.ciera.summit.classes.IInstanceIdentifier;
import io.ciera.summit.classes.IInstanceSet;
import io.ciera.summit.classes.IModelInstance;
import io.ciera.summit.util.UniqueId;

public abstract class InstanceSet implements IInstanceSet {
    
	private boolean immutable;
	private String keyLetters;
	
	private Set<IModelInstance> hashSet;
	private Map<UniqueId, IModelInstance> instanceIdSet;
	private Map<IInstanceIdentifier, IModelInstance> id1Set;
	private Map<IInstanceIdentifier, IModelInstance> id2Set;
	private Map<IInstanceIdentifier, IModelInstance> id3Set;
	
	public InstanceSet( String keyLetters ) {
		immutable = false;
		this.keyLetters = keyLetters;
		hashSet = new HashSet<>();
		instanceIdSet = new HashMap<>();
		id1Set = new HashMap<>();
		id2Set = new HashMap<>();
		id3Set = new HashMap<>();
	}

	@Override
	public String getKeyLetters() {
		return keyLetters;
	}

	@Override
	public int size() {
		return hashSet.size();
	}

	@Override
	public boolean isEmpty() {
		return ( 0 == size() );
	}

	@Override
	public boolean contains( Object o ) {
		return hashSet.contains( o );
	}

	@Override
	public Iterator<IModelInstance> iterator() {
		return hashSet.iterator();
	}

	@Override
	public Object[] toArray() {
		return hashSet.toArray();
	}

	@Override
	public <T> T[] toArray( T[] a ) {
		return hashSet.toArray( a );
	}

	@Override
	public boolean add( IModelInstance e ) {
		if ( !immutable && ( null != e && hashSet.add( e ) ) ) {
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
		if ( !immutable && ( null != o && hashSet.remove( o ) ) ) {
			instanceIdSet.remove( ((IModelInstance)o).getInstanceId() );
			if ( null != ((IModelInstance)o).getId1() ) id1Set.remove( ((IModelInstance)o).getId1() );
			if ( null != ((IModelInstance)o).getId2() )id2Set.remove( ((IModelInstance)o).getId2() );
			if ( null != ((IModelInstance)o).getId3() )id3Set.remove( ((IModelInstance)o).getId3() );
			return true;
		}
		else return false;
	}

	@Override
	public boolean containsAll( Collection<?> c ) {
		boolean containsAll = true;
		for ( Object o : c ) {
			containsAll = containsAll && contains( o );
		}
		return containsAll;
	}

	@Override
	public boolean addAll( Collection<? extends IModelInstance> c ) {
		boolean addAll = false;
		for ( IModelInstance e : c ) {
			addAll = add( e ) || addAll;
		}
		return addAll;
	}

	@Override
	public boolean retainAll( Collection<?> c ) {
		boolean retainAll = false;
		for ( IModelInstance e : hashSet ) {
			if ( !c.contains( e ) ) {
				retainAll = remove( e ) || retainAll;
			}
		}
		return retainAll;
	}

	@Override
	public boolean removeAll( Collection<?> c ) {
		boolean removeAll = false;
		for ( Object o : c ) {
			removeAll = remove( o ) || removeAll;
		}
		return removeAll;
	}

	@Override
	public void clear() {
		hashSet.clear();
		instanceIdSet.clear();
		id1Set.clear();
		id2Set.clear();
		id3Set.clear();
	}

	@Override
	public IModelInstance getByInstanceId( UniqueId instanceId ) {
		return instanceIdSet.get( instanceId );
	}

	@Override
	public IModelInstance getById1( IInstanceIdentifier id1 ) {
		return id1Set.get( id1 );
	}

	@Override
	public IModelInstance getById2( IInstanceIdentifier id2 ) {
		return id1Set.get( id2 );
	}

	@Override
	public IModelInstance getById3( IInstanceIdentifier id3 ) {
		return id1Set.get( id3 );
	}
	
	@Override
	public IInstanceSet toImmutableSet() {
		immutable = true;
		return this;
	}

}