package io.ciera.cairn.classes;

import java.util.HashMap;
import java.util.Map;

import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.classes.ISubsuperRelationship;
import io.ciera.summit.classes.ISubsuperRelationshipSet;
import io.ciera.summit.util.UniqueId;

public class SubsuperRelationshipSet extends RelationshipSet implements ISubsuperRelationshipSet {
	
	private Map<UniqueId, ISubsuperRelationshipSet> supertypeSetMap;
	private Map<UniqueId, ISubsuperRelationshipSet> subtypeSetMap;
	
	public SubsuperRelationshipSet( int relNum ) {
		super( relNum );
		supertypeSetMap = new HashMap<>();
		subtypeSetMap = new HashMap<>();
	}

	@Override
	public boolean add( IRelationship e ) {
		if ( super.add( e ) ) {
			ISubsuperRelationshipSet supertypeSet = supertypeSetMap.get( ((ISubsuperRelationship)e).getSupertype() );
			if ( null == supertypeSet ) {
				supertypeSet = (ISubsuperRelationshipSet)e.toSet();
				supertypeSetMap.put( ((ISubsuperRelationship)e).getSupertype(), supertypeSet );
			}
			else supertypeSet.add( e );
			ISubsuperRelationshipSet subtypeSet = subtypeSetMap.get( ((ISubsuperRelationship)e).getSubtype() );
			if ( null == subtypeSet ) {
				subtypeSet = (ISubsuperRelationshipSet)e.toSet();
				subtypeSetMap.put( ((ISubsuperRelationship)e).getSubtype(), subtypeSet );
			}
			else subtypeSet.add( e );
			return true;
		}
		else return false;
	}

	@Override
	public boolean remove( Object o ) {
		if ( super.remove( o ) ) {
			ISubsuperRelationshipSet supertypeSet = supertypeSetMap.get( ((ISubsuperRelationship)o).getSupertype() );
			if ( null != supertypeSet ) supertypeSet.remove( o );
			ISubsuperRelationshipSet subtypeSet = subtypeSetMap.get( ((ISubsuperRelationship)o).getSubtype() );
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
	public ISubsuperRelationshipSet getBySupertypeId( UniqueId supertypeId ) {
		return supertypeSetMap.get( supertypeId );
	}

	@Override
	public ISubsuperRelationshipSet getBySubtypeId( UniqueId subtypeId ) {
		return subtypeSetMap.get( subtypeId );
	}

	@Override
	public ISubsuperRelationship getByInstanceIds( UniqueId supertypeId, UniqueId subtypeId ) {
		ISubsuperRelationshipSet supertypeSet = getBySupertypeId( supertypeId );
		if ( null != supertypeSet ) {
			ISubsuperRelationshipSet subtypeSet = ((ISubsuperRelationshipSet)supertypeSet).getBySubtypeId( subtypeId );
			if ( null != subtypeSet && 1 == subtypeSet.size() ) return subtypeSet.toArray( new ISubsuperRelationship[0] )[0];
			else return null;
		}
		else return null;
	}

}
