package io.ciera.cairn.classes;

import java.util.HashMap;
import java.util.Map;
import io.ciera.summit.classes.IAssociativeRelationship;
import io.ciera.summit.classes.IAssociativeRelationshipSet;
import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.classes.UniqueId;

public class AssociativeRelationshipSet extends RelationshipSet implements IAssociativeRelationshipSet {
	
	private Map<UniqueId, IAssociativeRelationshipSet> oneSetMap;
	private Map<UniqueId, IAssociativeRelationshipSet> otherSetMap;
	private Map<UniqueId, IAssociativeRelationshipSet> linkSetMap;
	
	public AssociativeRelationshipSet( int relNum ) {
		super( relNum );
		oneSetMap = new HashMap<>();
		otherSetMap = new HashMap<>();
		linkSetMap = new HashMap<>();
	}

	@Override
	public boolean add( IRelationship e ) {
		if ( super.add( e ) ) {
			IAssociativeRelationshipSet oneSet = oneSetMap.get( ((IAssociativeRelationship)e).getOne() );
			if ( null == oneSet ) {
				oneSet = (IAssociativeRelationshipSet)e.toSet();
				oneSetMap.put( ((IAssociativeRelationship)e).getOne(), oneSet );
			}
			else oneSet.add( e );
			IAssociativeRelationshipSet otherSet = otherSetMap.get( ((IAssociativeRelationship)e).getOther() );
			if ( null == otherSet ) {
				otherSet = (IAssociativeRelationshipSet)e.toSet();
				otherSetMap.put( ((IAssociativeRelationship)e).getOther(), otherSet );
			}
			else otherSet.add( e );
			IAssociativeRelationshipSet linkSet = linkSetMap.get( ((IAssociativeRelationship)e).getLink() );
			if ( null == linkSet ) {
				linkSet = (IAssociativeRelationshipSet)e.toSet();
				linkSetMap.put( ((IAssociativeRelationship)e).getLink(), linkSet );
			}
			else linkSet.add( e );
			return true;
		}
		else return false;
	}

	@Override
	public boolean remove( Object o ) {
		if ( super.remove( o ) ) {
			IAssociativeRelationshipSet oneSet = oneSetMap.get( ((IAssociativeRelationship)o).getOne() );
			if ( null != oneSet ) oneSet.remove( o );
			IAssociativeRelationshipSet otherSet = otherSetMap.get( ((IAssociativeRelationship)o).getOther() );
			if ( null != otherSet ) otherSet.remove( o );
			IAssociativeRelationshipSet linkSet = linkSetMap.get( ((IAssociativeRelationship)o).getLink() );
			if ( null != linkSet ) linkSet.remove( o );
			return true;
		}
		else return false;
	}

	@Override
	public void clear() {
		super.clear();
		oneSetMap.clear();
		otherSetMap.clear();
		linkSetMap.clear();
	}

	@Override
	public IAssociativeRelationshipSet getByOneId( UniqueId oneId ) {
		return oneSetMap.get( oneId );
	}

	@Override
	public IAssociativeRelationshipSet getByOtherId( UniqueId otherId ) {
		return otherSetMap.get( otherId );
	}

	@Override
	public IAssociativeRelationshipSet getByLinkId( UniqueId linkId ) {
		return linkSetMap.get( linkId );
	}

	@Override
	public IAssociativeRelationship getByInstanceIds( UniqueId oneId, UniqueId otherId, UniqueId linkId ) {
		IAssociativeRelationshipSet oneSet = getByOneId( oneId );
		if ( null != oneSet ) {
			IAssociativeRelationshipSet otherSet = ((IAssociativeRelationshipSet)oneSet).getByOtherId( otherId );
			if ( null != otherSet ) {
				IAssociativeRelationshipSet linkSet = ((IAssociativeRelationshipSet)otherSet).getByLinkId( linkId );
				if ( null != linkSet && 1 == linkSet.size() ) return linkSet.toArray( new IAssociativeRelationship[0] )[0];
				else return null;
			}
			else return null;
		}
		else return null;
	}

}
