package io.ciera.summit.classes;

import java.util.Set;

public interface IBinaryRelationshipSet extends IRelationshipSet {
	
	public Set<IRelationship> getByOneId( UniqueId oneId );
	public Set<IRelationship> getByOtherId( UniqueId otherId );
	public IBinaryRelationship getByInstanceIds( UniqueId oneId, UniqueId otherId );

}
