package io.ciera.cairn.classes;

import java.util.Map;

import io.ciera.summit.classes.IInstancePopulation;
import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.classes.IRelationshipSet;
import io.ciera.summit.exceptions.BadArgumentException;
import io.ciera.summit.exceptions.InstancePopulationException;
import io.ciera.summit.exceptions.XtumlException;

public abstract class InstancePopulation implements IInstancePopulation {

    private Map<Integer, IRelationshipSet> relationshipPopulation;
    
    public InstancePopulation() {
    	relationshipPopulation = initializeRelationshipSets();
    }

    @Override
    public IRelationshipSet getRelationshipSet( int relNum ) throws XtumlException {
    	IRelationshipSet set = relationshipPopulation.get( relNum );
    	if ( null != set ) return set;
    	else throw new InstancePopulationException( "Relationship not supported by this instance population." );
    }

	@Override
	public boolean addRelationship( IRelationship relationship ) throws XtumlException {
    	if ( null == relationship ) throw new BadArgumentException( "Null relationship passed." );
    	return getRelationshipSet( relationship.getNumber() ).add( relationship );
	}

	@Override
	public boolean removeRelationship( IRelationship relationship ) throws XtumlException {
    	if ( null == relationship ) throw new BadArgumentException( "Null relationship passed." );
    	return getRelationshipSet( relationship.getNumber() ).remove( relationship );
	}

}
