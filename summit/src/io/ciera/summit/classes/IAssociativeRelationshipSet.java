package io.ciera.summit.classes;

import io.ciera.summit.util.UniqueId;

public interface IAssociativeRelationshipSet extends IRelationshipSet {

	public IRelationship getByOneId( UniqueId oneId );
	public IRelationship getByOtherId( UniqueId otherId );
	public IRelationship getByLinkId( UniqueId linkId );

}
