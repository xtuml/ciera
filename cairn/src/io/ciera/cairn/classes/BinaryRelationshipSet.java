package io.ciera.cairn.classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.ciera.summit.classes.IBinaryRelationship;
import io.ciera.summit.classes.IBinaryRelationshipSet;
import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.classes.UniqueId;

public class BinaryRelationshipSet extends RelationshipSet implements IBinaryRelationshipSet {
	
	private Map<UniqueId, Set<IRelationship>> oneSetMap;
	private Map<UniqueId, Set<IRelationship>> otherSetMap;
	
	public BinaryRelationshipSet( int relNum ) {
		super( relNum );
		oneSetMap = new HashMap<>();
		otherSetMap = new HashMap<>();
	}

	@Override
	public boolean add( IRelationship e ) {
		if ( super.add( e ) ) {
			Set<IRelationship> oneSet = oneSetMap.get( ((IBinaryRelationship)e).getOne() );
			if ( null == oneSet ) {
				oneSet = new HashSet<>();
				oneSetMap.put( ((IBinaryRelationship)e).getOne(), oneSet );
			}
			oneSet.add( e );
			Set<IRelationship> otherSet = otherSetMap.get( ((IBinaryRelationship)e).getOther() );
			if ( null == otherSet ) {
				otherSet = new HashSet<>();
				otherSetMap.put( ((IBinaryRelationship)e).getOther(), otherSet );
			}
			otherSet.add( e );
			return true;
		}
		else return false;
	}

	@Override
	public boolean remove( Object o ) {
		if ( super.remove( o ) ) {
			Set<IRelationship> oneSet = oneSetMap.get( ((IBinaryRelationship)o).getOne() );
			if ( null != oneSet ) oneSet.remove( o );
			Set<IRelationship> otherSet = otherSetMap.get( ((IBinaryRelationship)o).getOther() );
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
	public Set<IRelationship> getByOneId( UniqueId oneId ) {
		Set<IRelationship> returnSet = oneSetMap.get( oneId );
		if ( null != returnSet ) return returnSet;
		else return new HashSet<>();
	}

	@Override
	public Set<IRelationship> getByOtherId( UniqueId otherId ) {
		Set<IRelationship> returnSet = otherSetMap.get( otherId );
		if ( null != returnSet ) return returnSet;
		else return new HashSet<>();
	}

	@Override
	public IBinaryRelationship getByInstanceIds( UniqueId oneId, UniqueId otherId ) {
		Set<IRelationship> returnSet = getByOneId( oneId );
		returnSet.retainAll( getByOtherId( otherId ) );
	    if ( null != returnSet && 1 == returnSet.size() ) return returnSet.toArray( new IBinaryRelationship[0] )[0];
		else return null;
	}

}
