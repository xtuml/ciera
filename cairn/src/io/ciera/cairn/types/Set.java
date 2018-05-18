package io.ciera.cairn.types;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import io.ciera.summit.exceptions.BadArgumentException;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.ISet;
import io.ciera.summit.types.IWhere;

public abstract class Set<S extends ISet<S,E>,E> implements ISet<S,E> {


	private boolean immutable;
	private HashSet<E> internalSet;
	
	public Set() {
		internalSet = new HashSet<>();
		immutable = false;
	}

	public Set( Collection<? extends E> c ) {
		internalSet = new HashSet<>( c );
		immutable = false;
	}

	public Set( int initialCapacity ) {
		internalSet = new HashSet<>( initialCapacity );
		immutable = false;
	}

	public Set( int initialCapacity, float loadFactor ) {
		internalSet = new HashSet<>( initialCapacity, loadFactor );
		immutable = false;
	}

	@Override
	public S union( S set ) {
		S returnSet = emptySet();
		returnSet.addAll( this );
		returnSet.addAll( set );
		return returnSet.toImmutableSet();
	}

	@Override
	public S intersection( S set ) {
		S returnSet = emptySet();
		returnSet.addAll( this );
		returnSet.retainAll( set );       
		return returnSet.toImmutableSet();
	}

	@Override
	public S difference( S set ) {
		S returnSet = emptySet();
		returnSet.addAll( this );
		returnSet.removeAll( set );
		return returnSet.toImmutableSet();
	}

	@Override
	public S disunion( S set ) {
		S returnSet = union( set );
		returnSet.removeAll( intersection( set ) );
		return returnSet.toImmutableSet();
	}

	@Override
	public E any() {
		if ( !isEmpty() ) return iterator().next();
		else return nullElement();
	}

	@Override
	public S where( IWhere<E> condition ) throws XtumlException {
		if ( null == condition ) throw new BadArgumentException( "Null condition passed to selection." );
		S resultSet = emptySet();
	    for ( E selected : this ) {
			if ( condition.evaluate( selected ) ) resultSet.add( selected );
		}
		return resultSet.toImmutableSet();
	}
	
	@Override
	public void setImmutable() {
		immutable = true;
	}

	@Override
	public S toImmutableSet() {
		S returnSet = emptySet();
		returnSet.addAll( this );
		returnSet.setImmutable();
		return returnSet;
	}

	@Override
	public boolean add( E e ) {
		if ( immutable || null == e || e.equals( nullElement() ) ) return false;
		else return internalSet.add( e );
	}

	@Override
	public boolean remove(Object o) {
		if ( immutable || null == o || o.equals( nullElement() ) ) return false;
		else return internalSet.remove( o );
	}

	@Override
	public boolean addAll( Collection<? extends E> c ) {
		boolean addAll = false;
		for ( E e : c ) {
			addAll = add( e ) || addAll;
		}
		return addAll;
	}

	@Override
	public boolean retainAll( Collection<?> c ) {
		boolean retainAll = false;
		for ( E e : this ) {
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

	// Methods inherited from HashSet
	@Override
	public int size() {
		return internalSet.size();
	}

	@Override
	public boolean isEmpty() {
		return internalSet.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return internalSet.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return internalSet.iterator();
	}

	@Override
	public Object[] toArray() {
		return internalSet.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return internalSet.toArray(a);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return internalSet.containsAll(c);
	}

	@Override
	public void clear() {
		internalSet.clear();
	}
	
}
