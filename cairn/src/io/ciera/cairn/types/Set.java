package io.ciera.cairn.types;

import java.util.Collection;
import java.util.HashSet;

import io.ciera.summit.types.ISet;

public class Set<E> extends HashSet<E> implements ISet<E> {

	private static final long serialVersionUID = 1L;
	
	private boolean immutable;
	
	public Set() {
		super();
		immutable = false;
	}

	public Set( Collection<? extends E> c ) {
		super( c );
		immutable = false;
	}

	public Set( int initialCapacity ) {
		super( initialCapacity );
		immutable = false;
	}

	public Set( int initialCapacity, float loadFactor ) {
		super( initialCapacity, loadFactor );
		immutable = false;
	}

	@Override
	public ISet<E> union( ISet<E> set ) {
		ISet<E> returnSet = new Set<>( this );
		returnSet.addAll( set );
		return returnSet.toImmutableSet();
	}

	@Override
	public ISet<E> intersection( ISet<E> set ) {
		ISet<E> returnSet = new Set<>( this );
		returnSet.retainAll( set );       
		return returnSet;
	}

	@Override
	public ISet<E> difference( ISet<E> set ) {
		ISet<E> returnSet = new Set<>( this );
		returnSet.removeAll( set );
		return returnSet;
	}

	@Override
	public ISet<E> disunion( ISet<E> set ) {
		ISet<E> returnSet = this.union( set );
		returnSet.removeAll( this.intersection( set ) );
		return returnSet;
	}

	@Override
	public ISet<E> toImmutableSet() {
		immutable = true;
		return this;
	}

	@Override
	public boolean add( E e ) {
		if ( immutable ) return false;
		else return add( e );
	}

	@Override
	public boolean remove(Object o) {
		if ( immutable ) return false;
		else return remove( o );
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

}
