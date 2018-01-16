package io.ciera.summit.classes;

import io.ciera.summit.util.UniqueId;

public interface IBinaryRelationshipSet extends IRelationshipSet {
	
	public IRelationship getByOneId( UniqueId oneId );
	public IRelationship getByOtherId( UniqueId otherId );

}
