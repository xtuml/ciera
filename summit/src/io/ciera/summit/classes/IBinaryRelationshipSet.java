package io.ciera.summit.classes;

public interface IBinaryRelationshipSet extends IRelationshipSet {
	
	public IBinaryRelationshipSet getByOneId( UniqueId oneId );
	public IBinaryRelationshipSet getByOtherId( UniqueId otherId );
	public IBinaryRelationship getByInstanceIds( UniqueId oneId, UniqueId otherId );

}
