package io.ciera.cairn.classes;

import io.ciera.cairn.types.Set;
import io.ciera.summit.classes.IInstanceIdentifier;
import io.ciera.summit.classes.IInstanceSet;
import io.ciera.summit.classes.IModelInstance;
import io.ciera.summit.classes.UniqueId;
import io.ciera.summit.exceptions.XtumlException;

public abstract class InstanceSet<S extends IInstanceSet<S,E>,E extends IModelInstance> extends Set<S,E> implements IInstanceSet<S,E> {

	@Override
	public E getByInstanceId( UniqueId instanceId ) throws XtumlException {
		return where( selected -> selected.getInstanceId().equals( instanceId ) ).any();
	}

	@Override
	public E getById1( IInstanceIdentifier id1 ) throws XtumlException {
		return where( selected -> selected.getId1().equals( id1 ) ).any();
	}

	@Override
	public E getById2( IInstanceIdentifier id2 ) throws XtumlException {
		return where( selected -> selected.getId2().equals( id2 ) ).any();
	}

	@Override
	public E getById3( IInstanceIdentifier id3 ) throws XtumlException {
		return where( selected -> selected.getId3().equals( id3 ) ).any();
	}
	
}