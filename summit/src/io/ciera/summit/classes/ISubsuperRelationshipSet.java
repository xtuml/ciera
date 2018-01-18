package io.ciera.summit.classes;

import io.ciera.summit.util.UniqueId;

public interface ISubsuperRelationshipSet extends IRelationshipSet {

	public ISubsuperRelationshipSet getBySupertypeId( UniqueId supertypeId );
	public ISubsuperRelationshipSet getBySubtypeId( UniqueId subtypeId );
	public ISubsuperRelationship getByInstanceIds( UniqueId supertypeId, UniqueId subtypeId );
}
