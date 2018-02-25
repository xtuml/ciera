package io.ciera.cairn.classes;

import java.util.Collection;
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

	private BinaryRelationshipSet( int relNum, Collection<IRelationship> c ) {
		this( relNum );
		addAll( c );
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
	public IBinaryRelationshipSet getByOneId( UniqueId oneId ) {
		return new BinaryRelationshipSet( getNumber(), oneSetMap.get( oneId ) );
	}

	@Override
	public IBinaryRelationshipSet getByOtherId( UniqueId otherId ) {
		return new BinaryRelationshipSet( getNumber(), otherSetMap.get( otherId ) );
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
