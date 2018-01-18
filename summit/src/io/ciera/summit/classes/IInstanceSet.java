package io.ciera.summit.classes;

import java.util.Set;

import io.ciera.summit.util.UniqueId;

public interface IInstanceSet extends Set<IModelInstance> {
	
	public String getKeyLetters();
	public IModelInstance getByInstanceId( UniqueId instanceId );
	public IModelInstance getById1( IInstanceIdentifier id1 );
	public IModelInstance getById2( IInstanceIdentifier id2 );
	public IModelInstance getById3( IInstanceIdentifier id3 );
	public IInstanceSet toImmutableSet();

}