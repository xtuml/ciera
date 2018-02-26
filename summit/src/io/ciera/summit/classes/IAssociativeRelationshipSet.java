package io.ciera.summit.classes;

import java.util.Set;

public interface IAssociativeRelationshipSet extends IRelationshipSet {

	public Set<IRelationship> getByOneId( UniqueId oneId );
	public Set<IRelationship> getByOtherId( UniqueId otherId );
	public Set<IRelationship> getByLinkId( UniqueId linkId );
	public IAssociativeRelationship getByInstanceIds( UniqueId oneId, UniqueId otherId, UniqueId linkId );

}
