package io.ciera.summit.classes;

import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.ISet;

public interface IInstanceSet<E extends IModelInstance> extends ISet<E> {
	
	public E getByInstanceId( UniqueId instanceId );
	public E getById1( IInstanceIdentifier id1 );
	public E getById2( IInstanceIdentifier id2 );
	public E getById3( IInstanceIdentifier id3 );
	
	public E any();
	public E anyWhere( IWhere condition ) throws XtumlException;
	public IInstanceSet<E> manyWhere( IWhere condition ) throws XtumlException;
	
	public E emptyInstance();
	public IInstanceSet<E> emptySet();

}