package io.ciera.cairn.classes;

import java.util.HashMap;
import java.util.Map;
import io.ciera.summit.classes.ISubsuperRelationship;
import io.ciera.summit.classes.ISubsuperRelationshipSet;
import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.util.UniqueId;

public class SubsuperRelationshipSet extends RelationshipSet implements ISubsuperRelationshipSet {
	
	private Map<UniqueId, IRelationship> supertypeSet;
	private Map<UniqueId, IRelationship> subtypeSet;
	
	public SubsuperRelationshipSet( int relNum ) {
		super( relNum );
		supertypeSet = new HashMap<>();
		subtypeSet = new HashMap<>();
	}

	@Override
	public boolean add( IRelationship e ) {
		if ( super.add( e ) ) {
			supertypeSet.put( ((ISubsuperRelationship)e).getSupertype(), e );
			subtypeSet.put( ((ISubsuperRelationship)e).getSubtype(), e );
			return true;
		}
		else return false;
	}

	@Override
	public boolean remove( Object o ) {
		if ( super.remove( o ) ) {
			supertypeSet.remove( ((ISubsuperRelationship)o).getSupertype() );
			subtypeSet.remove( ((ISubsuperRelationship)o).getSubtype() );
			return true;
			
		}
		else return false;
	}

	@Override
	public void clear() {
		super.clear();
		supertypeSet.clear();
		subtypeSet.clear();
	}

	@Override
	public IRelationship getBySupertypeId( UniqueId supertypeId ) {
		return supertypeSet.get( supertypeId );
	}

	@Override
	public IRelationship getBySubtypeId( UniqueId subtypeId ) {
		return subtypeSet.get( subtypeId );
	}

}
