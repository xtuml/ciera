package io.ciera.summit.classes;

import io.ciera.summit.util.UniqueId;

public interface IAssociativeRelationshipSet extends IRelationshipSet {

	public IRelationshipSet getByOneId( UniqueId oneId );
	public IRelationshipSet getByOtherId( UniqueId otherId );
	public IRelationshipSet getByLinkId( UniqueId linkId );
	public IRelationship getByInstanceIds( UniqueId oneId, UniqueId otherId, UniqueId linkId );

}
