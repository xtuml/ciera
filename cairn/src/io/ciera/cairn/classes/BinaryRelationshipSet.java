package io.ciera.cairn.classes;

import java.util.HashMap;
import java.util.Map;
import io.ciera.summit.classes.IBinaryRelationship;
import io.ciera.summit.classes.IBinaryRelationshipSet;
import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.classes.IRelationshipSet;
import io.ciera.summit.util.UniqueId;

public class BinaryRelationshipSet extends RelationshipSet implements IBinaryRelationshipSet {
	
	private Map<UniqueId, IRelationshipSet> oneSetMap;
	private Map<UniqueId, IRelationshipSet> otherSetMap;
	
	public BinaryRelationshipSet( int relNum ) {
		super( relNum );
		oneSetMap = new HashMap<>();
		otherSetMap = new HashMap<>();
	}

	@Override
	public boolean add( IRelationship e ) {
		if ( super.add( e ) ) {
			IRelationshipSet oneSet = oneSetMap.get( ((IBinaryRelationship)e).getOne() );
			if ( null == oneSet ) {
				oneSet = e.toSet();
				oneSetMap.put( ((IBinaryRelationship)e).getOne(), oneSet );
			}
			else oneSet.add( e );
			IRelationshipSet otherSet = otherSetMap.get( ((IBinaryRelationship)e).getOther() );
			if ( null == otherSet ) {
				otherSet = e.toSet();
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
			IRelationshipSet oneSet = oneSetMap.get( ((IBinaryRelationship)o).getOne() );
			if ( null != oneSet ) oneSet.remove( o );
			IRelationshipSet otherSet = otherSetMap.get( ((IBinaryRelationship)o).getOther() );
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
	public IRelationshipSet getByOneId( UniqueId oneId ) {
		return oneSetMap.get( oneId );
	}

	@Override
	public IRelationshipSet getByOtherId( UniqueId otherId ) {
		return otherSetMap.get( otherId );
	}

	@Override
	public IRelationship getByInstanceIds( UniqueId oneId, UniqueId otherId ) {
		IRelationshipSet oneSet = getByOneId( oneId );
		if ( null != oneSet ) {
			IRelationshipSet otherSet = ((IBinaryRelationshipSet)oneSet).getByOtherId( otherId );
			if ( null != otherSet && 1 == otherSet.size() ) return otherSet.toArray( new IRelationship[0] )[0];
			else return null;
		}
		else return null;
	}

}
