package io.ciera.summit.classes;

public interface IAssociativeRelationshipSet extends IRelationshipSet {

	public IAssociativeRelationshipSet getByOneId( UniqueId oneId );
	public IAssociativeRelationshipSet getByOtherId( UniqueId otherId );
	public IAssociativeRelationshipSet getByLinkId( UniqueId linkId );
	public IAssociativeRelationship getByInstanceIds( UniqueId oneId, UniqueId otherId, UniqueId linkId );

}
