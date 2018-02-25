package io.ciera.cairn.classes;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.ciera.summit.classes.IAssociativeRelationship;
import io.ciera.summit.classes.IAssociativeRelationshipSet;
import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.classes.UniqueId;

public class AssociativeRelationshipSet extends RelationshipSet implements IAssociativeRelationshipSet {
	
	private Map<UniqueId, Set<IRelationship>> oneSetMap;
	private Map<UniqueId, Set<IRelationship>> otherSetMap;
	private Map<UniqueId, Set<IRelationship>> linkSetMap;
	
	public AssociativeRelationshipSet( int relNum ) {
		super( relNum );
		oneSetMap = new HashMap<>();
		otherSetMap = new HashMap<>();
		linkSetMap = new HashMap<>();
	}
	
	private AssociativeRelationshipSet( int relNum, Collection<IRelationship> c ) {
		this( relNum );
		addAll( c );
	}

	@Override
	public boolean add( IRelationship e ) {
		if ( super.add( e ) ) {
			Set<IRelationship> oneSet = oneSetMap.get( ((IAssociativeRelationship)e).getOne() );
			if ( null == oneSet ) {
				oneSet = new HashSet<>();
				oneSetMap.put( ((IAssociativeRelationship)e).getOne(), oneSet );
			}
			oneSet.add( e );
			Set<IRelationship> otherSet = otherSetMap.get( ((IAssociativeRelationship)e).getOther() );
			if ( null == otherSet ) {
				otherSet = new HashSet<>();
				otherSetMap.put( ((IAssociativeRelationship)e).getOther(), otherSet );
			}
			otherSet.add( e );
			Set<IRelationship> linkSet = linkSetMap.get( ((IAssociativeRelationship)e).getLink() );
			if ( null == linkSet ) {
				linkSet = new HashSet<>();
				linkSetMap.put( ((IAssociativeRelationship)e).getLink(), linkSet );
			}
			linkSet.add( e );
			return true;
		}
		else return false;
	}

	@Override
	public boolean remove( Object o ) {
		if ( super.remove( o ) ) {
			Set<IRelationship> oneSet = oneSetMap.get( ((IAssociativeRelationship)o).getOne() );
			if ( null != oneSet ) oneSet.remove( o );
			Set<IRelationship> otherSet = otherSetMap.get( ((IAssociativeRelationship)o).getOther() );
			if ( null != otherSet ) otherSet.remove( o );
			Set<IRelationship> linkSet = linkSetMap.get( ((IAssociativeRelationship)o).getLink() );
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
		return new AssociativeRelationshipSet( getNumber(), oneSetMap.get( oneId ) );
	}

	@Override
	public IAssociativeRelationshipSet getByOtherId( UniqueId otherId ) {
		return new AssociativeRelationshipSet( getNumber(), otherSetMap.get( otherId ) );
	}

	@Override
	public IAssociativeRelationshipSet getByLinkId( UniqueId linkId ) {
		return new AssociativeRelationshipSet( getNumber(), linkSetMap.get( linkId ) );
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
