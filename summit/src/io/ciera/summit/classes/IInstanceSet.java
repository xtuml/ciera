package io.ciera.summit.classes;

import io.ciera.summit.types.ISet;

public interface IInstanceSet<S extends IInstanceSet<S,E>,E extends IModelInstance> extends ISet<S,E> {
	
	public E getByInstanceId( UniqueId instanceId );
	public E getById1( IInstanceIdentifier id1 );
	public E getById2( IInstanceIdentifier id2 );
	public E getById3( IInstanceIdentifier id3 );
	
}