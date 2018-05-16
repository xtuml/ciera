package io.ciera.cairn.classes;

import java.lang.reflect.Method;
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

    private Map<Integer, IRelationshipSet> relationshipPopulation;
    
    public InstancePopulation() {
    	relationshipPopulation = initializeRelationshipSets();
    }

	@Override
	public boolean addInstance( IModelInstance instance ) throws XtumlException {
    	if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
    	if ( instance instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot add empty instance to population." );
    	// TODO don't use reflection here. it is a temporary hack
    	try {
    	    Method selector = this.getClass().getMethod( instance.getClass().getName() + "instances" );
    		return ((IInstanceSet<IModelInstance>)selector.invoke( this )).add( instance );
    	}
    	catch ( Exception e ) {
    		return false;
    	}
	}

	@Override
	public boolean removeInstance( IModelInstance instance ) throws XtumlException {
    	if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
    	if ( instance instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot remove empty instance from population." );
        // TODO don't use reflection here. it is a temporary hack
    	try {
    	    Method selector = this.getClass().getMethod( instance.getClass().getName() + "instances" );
    		return ((IInstanceSet<IModelInstance>)selector.invoke( this )).remove( instance );
    	}
    	catch ( Exception e ) {
    		return false;
    	}
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
