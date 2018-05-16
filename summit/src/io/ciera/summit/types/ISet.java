package io.ciera.summit.types;

import java.util.Set;

public interface ISet<E> extends Set<E> {
	
	ISet<E> union( ISet<E> set );
	ISet<E> intersection( ISet<E> set );
	ISet<E> difference( ISet<E> set );
	ISet<E> disunion( ISet<E> set );
	
	ISet<E> toImmutableSet();

}
