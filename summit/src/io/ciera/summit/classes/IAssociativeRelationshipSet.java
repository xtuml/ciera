package io.ciera.summit.classes;

import io.ciera.summit.util.UniqueId;

public interface IAssociativeRelationshipSet extends IRelationshipSet {

	public IAssociativeRelationshipSet getByOneId( UniqueId oneId );
	public IAssociativeRelationshipSet getByOtherId( UniqueId otherId );
	public IAssociativeRelationshipSet getByLinkId( UniqueId linkId );
	public IAssociativeRelationship getByInstanceIds( UniqueId oneId, UniqueId otherId, UniqueId linkId );

}
