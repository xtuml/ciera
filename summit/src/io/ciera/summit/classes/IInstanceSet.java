package io.ciera.summit.classes;

import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.ISet;
import io.ciera.summit.types.IUniqueId;

public interface IInstanceSet<S extends IInstanceSet<S,E>,E extends IModelInstance<E,?>> extends ISet<S,E> {
	
	public E getByInstanceId( IUniqueId instanceId ) throws XtumlException;
	public E getById1( IInstanceIdentifier id1 ) throws XtumlException;
	public E getById2( IInstanceIdentifier id2 ) throws XtumlException;
	public E getById3( IInstanceIdentifier id3 ) throws XtumlException;
	
}