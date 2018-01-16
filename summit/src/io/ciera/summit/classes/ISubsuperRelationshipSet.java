package io.ciera.summit.classes;

import io.ciera.summit.util.UniqueId;

public interface ISubsuperRelationshipSet extends IRelationshipSet {

	public IRelationshipSet getBySupertypeId( UniqueId supertypeId );
	public IRelationshipSet getBySubtypeId( UniqueId subtypeId );
	public IRelationship getByInstanceIds( UniqueId supertypeId, UniqueId subtypeId );
}
