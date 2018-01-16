package io.ciera.cairn.classes;

import java.util.HashMap;
import java.util.Map;
import io.ciera.summit.classes.IBinaryRelationship;
import io.ciera.summit.classes.IBinaryRelationshipSet;
import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.util.UniqueId;

public class BinaryRelationshipSet extends RelationshipSet implements IBinaryRelationshipSet {
	
	private Map<UniqueId, IRelationship> oneSet;
	private Map<UniqueId, IRelationship> otherSet;
	
	public BinaryRelationshipSet( int relNum ) {
		super( relNum );
		oneSet = new HashMap<>();
		otherSet = new HashMap<>();
	}

	@Override
	public boolean add( IRelationship e ) {
		if ( super.add( e ) ) {
			oneSet.put( ((IBinaryRelationship)e).getOne(), e );
			otherSet.put( ((IBinaryRelationship)e).getOther(), e );
			return true;
		}
		else return false;
	}

	@Override
	public boolean remove( Object o ) {
		if ( super.remove( o ) ) {
			oneSet.remove( ((IBinaryRelationship)o).getOne() );
			otherSet.remove( ((IBinaryRelationship)o).getOther() );
			return true;
			
		}
		else return false;
	}

	@Override
	public void clear() {
		super.clear();
		oneSet.clear();
		otherSet.clear();
	}

	@Override
	public IRelationship getByOneId( UniqueId oneId ) {
		return oneSet.get( oneId );
	}

	@Override
	public IRelationship getByOtherId( UniqueId otherId ) {
		return otherSet.get( otherId );
	}

}
