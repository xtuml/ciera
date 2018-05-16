package io.ciera.summit.classes;

import java.util.function.Predicate;

import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.ISet;

public interface IInstanceSet<E extends IModelInstance> extends ISet<E> {
	
	public String getKeyLetters();

	public E getByInstanceId( UniqueId instanceId );
	public E getById1( IInstanceIdentifier id1 );
	public E getById2( IInstanceIdentifier id2 );
	public E getById3( IInstanceIdentifier id3 );
	
	public E any();
	public E anyWhere( Predicate<E> test ) throws XtumlException;
	public IInstanceSet<E> manyWhere( Predicate<E> test ) throws XtumlException;
	
	public E emptyInstance();
	public IInstanceSet<E> emptySet();

}