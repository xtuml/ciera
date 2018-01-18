package io.ciera.cairn.classes;

import java.util.Map;

import io.ciera.summit.classes.IEmptyInstance;
import io.ciera.summit.classes.IInstancePopulation;
import io.ciera.summit.classes.IInstanceSet;
import io.ciera.summit.classes.IModelInstance;
import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.classes.IRelationshipSet;
import io.ciera.summit.exceptions.BadArgumentException;
import io.ciera.summit.exceptions.EmptyInstanceException;
import io.ciera.summit.exceptions.InstancePopulationException;
import io.ciera.summit.exceptions.XtumlException;

public abstract class InstancePopulation implements IInstancePopulation {

    private Map<String, IInstanceSet> instancePopulation;
    private Map<Integer, IRelationshipSet> relationshipPopulation;
    
    public InstancePopulation() {
    	instancePopulation = initializeInstanceSets();
    	relationshipPopulation = initializeRelationshipSets();
    }

    @Override
    public IInstanceSet getInstanceSet( String keyLetters ) throws XtumlException {
    	IInstanceSet set = instancePopulation.get( keyLetters );
    	if ( null != set ) return set;
    	else throw new InstancePopulationException( "Class not supported by this instance population." );
    }

	@Override
	public boolean addInstance( IModelInstance instance ) throws XtumlException {
    	if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
    	if ( instance instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot add empty instance to population." );
    	return getInstanceSet( instance.getKeyLetters() ).add( instance );
	}

	@Override
	public boolean removeInstance( IModelInstance instance ) throws XtumlException {
    	if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
    	if ( instance instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot remove empty instance from population." );
    	return getInstanceSet( instance.getKeyLetters() ).remove( instance );
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

    @Override
    public void deleteObjectInstance( IModelInstance instance ) throws XtumlException {
    	if ( removeInstance( instance ) ) instance.delete();
        else throw new InstancePopulationException( "Instance does not exist within this population." );
    }

}