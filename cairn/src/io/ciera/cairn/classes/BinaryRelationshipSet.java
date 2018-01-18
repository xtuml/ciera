package io.ciera.cairn.classes;

import java.util.HashMap;
import java.util.Map;
import io.ciera.summit.classes.IBinaryRelationship;
import io.ciera.summit.classes.IBinaryRelationshipSet;
import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.util.UniqueId;

public class BinaryRelationshipSet extends RelationshipSet implements IBinaryRelationshipSet {
	
	private Map<UniqueId, IBinaryRelationshipSet> oneSetMap;
	private Map<UniqueId, IBinaryRelationshipSet> otherSetMap;
	
	public BinaryRelationshipSet( int relNum ) {
		super( relNum );
		oneSetMap = new HashMap<>();
		otherSetMap = new HashMap<>();
	}

	@Override
	public boolean add( IRelationship e ) {
		if ( super.add( e ) ) {
			IBinaryRelationshipSet oneSet = oneSetMap.get( ((IBinaryRelationship)e).getOne() );
			if ( null == oneSet ) {
				oneSet = (IBinaryRelationshipSet)e.toSet();
				oneSetMap.put( ((IBinaryRelationship)e).getOne(), oneSet );
			}
			else oneSet.add( e );
			IBinaryRelationshipSet otherSet = otherSetMap.get( ((IBinaryRelationship)e).getOther() );
			if ( null == otherSet ) {
				otherSet = (IBinaryRelationshipSet)e.toSet();
				otherSetMap.put( ((IBinaryRelationship)e).getOther(), otherSet );
			}
			else otherSet.add( e );
			return true;
		}
		else return false;
	}

	@Override
	public boolean remove( Object o ) {
		if ( super.remove( o ) ) {
			IBinaryRelationshipSet oneSet = oneSetMap.get( ((IBinaryRelationship)o).getOne() );
			if ( null != oneSet ) oneSet.remove( o );
			IBinaryRelationshipSet otherSet = otherSetMap.get( ((IBinaryRelationship)o).getOther() );
			if ( null != otherSet ) otherSet.remove( o );
			return true;
		}
		else return false;
	}

	@Override
	public void clear() {
		super.clear();
		oneSetMap.clear();
		otherSetMap.clear();
	}

	@Override
	public IBinaryRelationshipSet getByOneId( UniqueId oneId ) {
		return oneSetMap.get( oneId );
	}

	@Override
	public IBinaryRelationshipSet getByOtherId( UniqueId otherId ) {
		return otherSetMap.get( otherId );
	}

	@Override
	public IBinaryRelationship getByInstanceIds( UniqueId oneId, UniqueId otherId ) {
		IBinaryRelationshipSet oneSet = getByOneId( oneId );
		if ( null != oneSet ) {
			IBinaryRelationshipSet otherSet = ((IBinaryRelationshipSet)oneSet).getByOtherId( otherId );
			if ( null != otherSet && 1 == otherSet.size() ) return otherSet.toArray( new IBinaryRelationship[0] )[0];
			else return null;
		}
		else return null;
	}

}
