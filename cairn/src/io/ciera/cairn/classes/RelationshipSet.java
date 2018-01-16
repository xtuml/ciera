package io.ciera.cairn.classes;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.classes.IRelationshipSet;

public abstract class RelationshipSet implements IRelationshipSet {
	
	private int relNum;
	
	private Set<IRelationship> hashSet;
	
	public RelationshipSet( int relNum ) {
		this.relNum = relNum;
		hashSet = new HashSet<>();
	}

	@Override
	public int getNumber() {
		return relNum;
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
	public Iterator<IRelationship> iterator() {
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
	public boolean add( IRelationship e ) {
		if ( null != e ) return hashSet.add( e );
		else return false;
	}

	@Override
	public boolean remove( Object o ) {
		if ( null != o ) return hashSet.remove( o );
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
	public boolean addAll( Collection<? extends IRelationship> c ) {
		boolean addAll = false;
		for ( IRelationship e : c ) {
			addAll = add( e ) || addAll;
		}
		return addAll;
	}

	@Override
	public boolean retainAll( Collection<?> c ) {
		boolean retainAll = false;
		for ( IRelationship e : hashSet ) {
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
	}

}
