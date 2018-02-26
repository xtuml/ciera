package io.ciera.summit.classes;

import java.util.Set;

public interface ISubsuperRelationshipSet extends IRelationshipSet {

	public Set<IRelationship> getBySupertypeId( UniqueId supertypeId );
	public Set<IRelationship> getBySubtypeId( UniqueId subtypeId );
	public ISubsuperRelationship getByInstanceIds( UniqueId supertypeId, UniqueId subtypeId );
}
