package io.ciera.cairn.classes;

import java.util.HashMap;
import java.util.Map;
import io.ciera.summit.classes.IAssociativeRelationship;
import io.ciera.summit.classes.IAssociativeRelationshipSet;
import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.util.UniqueId;

public class AssociativeRelationshipSet extends RelationshipSet implements IAssociativeRelationshipSet {
	
	private Map<UniqueId, IRelationship> oneSet;
	private Map<UniqueId, IRelationship> otherSet;
	private Map<UniqueId, IRelationship> linkSet;
	
	public AssociativeRelationshipSet( int relNum ) {
		super( relNum );
		oneSet = new HashMap<>();
		otherSet = new HashMap<>();
		linkSet = new HashMap<>();
	}

	@Override
	public boolean add( IRelationship e ) {
		if ( super.add( e ) ) {
			oneSet.put( ((IAssociativeRelationship)e).getOne(), e );
			otherSet.put( ((IAssociativeRelationship)e).getOther(), e );
			linkSet.put( ((IAssociativeRelationship)e).getLink(), e );
			return true;
		}
		else return false;
	}

	@Override
	public boolean remove( Object o ) {
		if ( super.remove( o ) ) {
			oneSet.remove( ((IAssociativeRelationship)o).getOne() );
			otherSet.remove( ((IAssociativeRelationship)o).getOther() );
			linkSet.remove( ((IAssociativeRelationship)o).getLink() );
			return true;
			
		}
		else return false;
	}

	@Override
	public void clear() {
		super.clear();
		oneSet.clear();
		otherSet.clear();
		linkSet.clear();
	}

	@Override
	public IRelationship getByOneId( UniqueId oneId ) {
		return oneSet.get( oneId );
	}

	@Override
	public IRelationship getByOtherId( UniqueId otherId ) {
		return otherSet.get( otherId );
	}

	@Override
	public IRelationship getByLinkId( UniqueId linkId ) {
		return linkSet.get( linkId );
	}

}
