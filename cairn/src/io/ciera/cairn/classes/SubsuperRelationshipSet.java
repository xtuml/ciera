package io.ciera.cairn.classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.classes.ISubsuperRelationship;
import io.ciera.summit.classes.ISubsuperRelationshipSet;
import io.ciera.summit.classes.UniqueId;

public class SubsuperRelationshipSet extends RelationshipSet implements ISubsuperRelationshipSet {
	
	private Map<UniqueId, Set<IRelationship>> supertypeSetMap;
	private Map<UniqueId, Set<IRelationship>> subtypeSetMap;
	
	public SubsuperRelationshipSet( int relNum ) {
		super( relNum );
		supertypeSetMap = new HashMap<>();
		subtypeSetMap = new HashMap<>();
	}

	@Override
	public boolean add( IRelationship e ) {
		if ( super.add( e ) ) {
			Set<IRelationship> supertypeSet = supertypeSetMap.get( ((ISubsuperRelationship)e).getSupertype() );
			if ( null == supertypeSet ) {
				supertypeSet = new HashSet<>();
				supertypeSetMap.put( ((ISubsuperRelationship)e).getSupertype(), supertypeSet );
			}
			supertypeSet.add( e );
			Set<IRelationship> subtypeSet = subtypeSetMap.get( ((ISubsuperRelationship)e).getSubtype() );
			if ( null == subtypeSet ) {
				subtypeSet = new HashSet<>();
				subtypeSetMap.put( ((ISubsuperRelationship)e).getSubtype(), subtypeSet );
			}
			subtypeSet.add( e );
			return true;
		}
		else return false;
	}

	@Override
	public boolean remove( Object o ) {
		if ( super.remove( o ) ) {
			Set<IRelationship> supertypeSet = supertypeSetMap.get( ((ISubsuperRelationship)o).getSupertype() );
			if ( null != supertypeSet ) supertypeSet.remove( o );
			Set<IRelationship> subtypeSet = subtypeSetMap.get( ((ISubsuperRelationship)o).getSubtype() );
			if ( null != subtypeSet ) subtypeSet.remove( o );
			return true;
		}
		else return false;
	}

	@Override
	public void clear() {
		super.clear();
		supertypeSetMap.clear();
		subtypeSetMap.clear();
	}

	@Override
	public Set<IRelationship> getBySupertypeId( UniqueId supertypeId ) {
		Set<IRelationship> returnSet = supertypeSetMap.get( supertypeId );
		if ( null != returnSet ) return returnSet;
		else return new HashSet<>();
	}

	@Override
	public Set<IRelationship> getBySubtypeId( UniqueId subtypeId ) {
		Set<IRelationship> returnSet = subtypeSetMap.get( subtypeId );
		if ( null != returnSet ) return returnSet;
		else return new HashSet<>();
	}

	@Override
	public ISubsuperRelationship getByInstanceIds( UniqueId supertypeId, UniqueId subtypeId ) {
		Set<IRelationship> returnSet = getBySupertypeId( supertypeId );
		returnSet.retainAll( getBySubtypeId( subtypeId ) );
		if ( null != returnSet && 1 == returnSet.size() ) return returnSet.toArray( new ISubsuperRelationship[0] )[0];
		else return null;
	}

}
