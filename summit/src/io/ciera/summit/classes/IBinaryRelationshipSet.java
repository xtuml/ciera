package io.ciera.summit.classes;

import io.ciera.summit.util.UniqueId;

public interface IBinaryRelationshipSet extends IRelationshipSet {
	
	public IRelationshipSet getByOneId( UniqueId oneId );
	public IRelationshipSet getByOtherId( UniqueId otherId );
	public IRelationship getByInstanceIds( UniqueId oneId, UniqueId otherId );

}
