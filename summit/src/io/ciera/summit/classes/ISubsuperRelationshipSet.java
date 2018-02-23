package io.ciera.summit.classes;

public interface ISubsuperRelationshipSet extends IRelationshipSet {

	public ISubsuperRelationshipSet getBySupertypeId( UniqueId supertypeId );
	public ISubsuperRelationshipSet getBySubtypeId( UniqueId subtypeId );
	public ISubsuperRelationship getByInstanceIds( UniqueId supertypeId, UniqueId subtypeId );
}
