package io.ciera.cairn.classes;

import java.util.HashMap;
import java.util.Map;
import io.ciera.summit.classes.IAssociativeRelationship;
import io.ciera.summit.classes.IAssociativeRelationshipSet;
import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.classes.IRelationshipSet;
import io.ciera.summit.util.UniqueId;

public class AssociativeRelationshipSet extends RelationshipSet implements IAssociativeRelationshipSet {
	
	private Map<UniqueId, IRelationshipSet> oneSetMap;
	private Map<UniqueId, IRelationshipSet> otherSetMap;
	private Map<UniqueId, IRelationshipSet> linkSetMap;
	
	public AssociativeRelationshipSet( int relNum ) {
		super( relNum );
		oneSetMap = new HashMap<>();
		otherSetMap = new HashMap<>();
		linkSetMap = new HashMap<>();
	}

	@Override
	public boolean add( IRelationship e ) {
		if ( super.add( e ) ) {
			IRelationshipSet oneSet = oneSetMap.get( ((IAssociativeRelationship)e).getOne() );
			if ( null == oneSet ) {
				oneSet = e.toSet();
				oneSetMap.put( ((IAssociativeRelationship)e).getOne(), oneSet );
			}
			else oneSet.add( e );
			IRelationshipSet otherSet = otherSetMap.get( ((IAssociativeRelationship)e).getOther() );
			if ( null == otherSet ) {
				otherSet = e.toSet();
				otherSetMap.put( ((IAssociativeRelationship)e).getOther(), otherSet );
			}
			else otherSet.add( e );
			IRelationshipSet linkSet = linkSetMap.get( ((IAssociativeRelationship)e).getLink() );
			if ( null == linkSet ) {
				linkSet = e.toSet();
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
			IRelationshipSet oneSet = oneSetMap.get( ((IAssociativeRelationship)o).getOne() );
			if ( null != oneSet ) oneSet.remove( o );
			IRelationshipSet otherSet = otherSetMap.get( ((IAssociativeRelationship)o).getOther() );
			if ( null != otherSet ) otherSet.remove( o );
			IRelationshipSet linkSet = linkSetMap.get( ((IAssociativeRelationship)o).getLink() );
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
	public IRelationshipSet getByOneId( UniqueId oneId ) {
		return oneSetMap.get( oneId );
	}

	@Override
	public IRelationshipSet getByOtherId( UniqueId otherId ) {
		return otherSetMap.get( otherId );
	}

	@Override
	public IRelationshipSet getByLinkId( UniqueId linkId ) {
		return linkSetMap.get( linkId );
	}

	@Override
	public IRelationship getByInstanceIds( UniqueId oneId, UniqueId otherId, UniqueId linkId ) {
		IRelationshipSet oneSet = getByOneId( oneId );
		if ( null != oneSet ) {
			IRelationshipSet otherSet = ((IAssociativeRelationshipSet)oneSet).getByOtherId( otherId );
			if ( null != otherSet ) {
				IRelationshipSet linkSet = ((IAssociativeRelationshipSet)otherSet).getByLinkId( linkId );
				if ( null != linkSet && 1 == linkSet.size() ) return linkSet.toArray( new IRelationship[0] )[0];
				else return null;
			}
			else return null;
		}
		else return null;
	}

}
