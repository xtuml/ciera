package io.ciera.summit.classes;

import io.ciera.summit.util.UniqueId;

public interface ISubsuperRelationshipSet extends IRelationshipSet {

	public IRelationship getBySupertypeId( UniqueId supertypeId );
	public IRelationship getBySubtypeId( UniqueId subtypeId );
}
