package io.ciera.cairn.classes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import io.ciera.summit.classes.IAssociativeRelationshipSet;
import io.ciera.summit.classes.IBinaryRelationshipSet;
import io.ciera.summit.classes.IEmptyInstance;
import io.ciera.summit.classes.IInstancePopulation;
import io.ciera.summit.classes.IInstanceSet;
import io.ciera.summit.classes.IModelInstance;
import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.classes.IRelationshipSet;
import io.ciera.summit.classes.ISubsuperRelationshipSet;
import io.ciera.summit.exceptions.EmptyInstanceException;
import io.ciera.summit.exceptions.InstancePopulationException;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.util.UniqueId;

public abstract class InstancePopulation implements IInstancePopulation {

    private Map<String, IInstanceSet> instancePopulation;
    private Map<Integer, IRelationshipSet> relationshipPopulation;
    
    public InstancePopulation() {
    	instancePopulation = new HashMap<>();
    	relationshipPopulation = new HashMap<>();
    }

    @Override
    public IInstanceSet getInstanceSet( String keyLetters ) {
        return instancePopulation.get( keyLetters );
    }

    @Override
    public IModelInstance createObjectInstance( String keyLetters ) throws XtumlException {
        IInstanceSet instanceSet = instancePopulation.get( keyLetters );
        if ( null != instanceSet ) {
			try {
                Constructor<?> newInstanceConstructor = getClasses().get( keyLetters ).getConstructor( IInstancePopulation.class );
                IModelInstance newInstance = (IModelInstance)newInstanceConstructor.newInstance( this );
                instanceSet.add( newInstance );
                return newInstance;
			} catch ( NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e ) {
				throw new InstancePopulationException( "Error creating instance." );
			}
        }
        else throw new InstancePopulationException( "Class does not exist within this population." );
    }

    @Override
    public void deleteObjectInstance( IModelInstance instance ) throws XtumlException {
    	if ( null == instance || instance instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot delete empty instance." );
        IInstanceSet instanceSet = instancePopulation.get( instance.getKeyLetters() );
        if ( null != instanceSet ) {
            instance.delete();
            instanceSet.remove( instance );
        }
        else throw new InstancePopulationException( "Class does not exist within this population." );
    }
    
    @Override
    public IRelationshipSet getRelationshipSet( int relNum ) {
    	return relationshipPopulation.get( relNum );
    }

	@Override
	public void relateAcross( int relNum, IModelInstance ... instances ) throws XtumlException {
		if ( null == instances ) throw new InstancePopulationException( "Null instances passed." );
		for ( IModelInstance instance : instances ) {
    	    if ( null == instance || instance instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot relate empty instance." );
		}
        IRelationshipSet relationshipSet = relationshipPopulation.get( relNum );
        if ( null != relationshipSet ) {
			try {
				IRelationship newRelationship = null;
                if ( relationshipSet instanceof IBinaryRelationshipSet ) {
                    if ( 2 == instances.length ) {
                        Constructor<?> newRelationshipConstructor = getRelationships().get( relNum ).getConstructor( IInstancePopulation.class, UniqueId.class, UniqueId.class );
                        newRelationship = (IRelationship)newRelationshipConstructor.newInstance( this, instances[0].getInstanceId(), instances[1].getInstanceId() );
                    }
                    else throw new InstancePopulationException( "Wrong number of instances passed." );
                }
                else if ( relationshipSet instanceof IAssociativeRelationshipSet ) {
                    if ( 3 == instances.length ) {
                        Constructor<?> newRelationshipConstructor = getRelationships().get( relNum ).getConstructor( IInstancePopulation.class, UniqueId.class, UniqueId.class, UniqueId.class );
                        newRelationship = (IRelationship)newRelationshipConstructor.newInstance( this, instances[0].getInstanceId(), instances[1].getInstanceId(), instances[2].getInstanceId() );
                    }
                    else throw new InstancePopulationException( "Wrong number of instances passed." );
                }
                else if ( relationshipSet instanceof ISubsuperRelationshipSet ) {
                    if ( 2 == instances.length ) {
                        Constructor<?> newRelationshipConstructor = getRelationships().get( relNum ).getConstructor( IInstancePopulation.class, UniqueId.class, UniqueId.class );
                        newRelationship = (IRelationship)newRelationshipConstructor.newInstance( this, instances[0].getInstanceId(), instances[1].getInstanceId() );
                    }
                    else throw new InstancePopulationException( "Wrong number of instances passed." );
                }
                else throw new InstancePopulationException( "Unknown relationship set." );
                relationshipSet.add( newRelationship );
			} catch ( NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e ) {
				throw new InstancePopulationException( "Error relating instances." );
			}
        }
        else throw new InstancePopulationException( "Relationship does not exist within this population." );
 
	}

	@Override
	public void unrelateAcross( int relNum, IModelInstance ... instances ) throws XtumlException {
		if ( null == instances ) throw new InstancePopulationException( "Null instances passed." );
		for ( IModelInstance instance : instances ) {
    	    if ( null == instance || instance instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot unrelate empty instance." );
		}
        IRelationshipSet relationshipSet = relationshipPopulation.get( relNum );
        if ( null != relationshipSet ) {
        	IRelationship relationship = null;
        	if ( relationshipSet instanceof IBinaryRelationshipSet ) {
        		if ( 2 == instances.length ) relationship = ((IBinaryRelationshipSet)relationshipSet).getByInstanceIds( instances[0].getInstanceId(), instances[1].getInstanceId() );
        		else throw new InstancePopulationException( "Wrong number of instances passed." );
        	}
        	else if ( relationshipSet instanceof IAssociativeRelationshipSet ) {
        		if ( 3 == instances.length ) relationship = ((IAssociativeRelationshipSet) relationshipSet).getByInstanceIds( instances[0].getInstanceId(), instances[1].getInstanceId(), instances[2].getInstanceId() );
        		else throw new InstancePopulationException( "Wrong number of instances passed." );
        	}
        	else if ( relationshipSet instanceof ISubsuperRelationshipSet ) {
        		if ( 2 == instances.length ) relationship = ((ISubsuperRelationshipSet)relationshipSet).getByInstanceIds( instances[0].getInstanceId(), instances[1].getInstanceId() );
        		else throw new InstancePopulationException( "Wrong number of instances passed." );
        	}
        	else throw new InstancePopulationException( "Unknown relationship set." );
        	if ( null != relationship ) {
        		relationship.delete();
        		relationshipSet.remove( relationship );
        	}
        	else throw new InstancePopulationException( "Instances are not related." );
        }
        else throw new InstancePopulationException( "Relationship does not exist within this population." );
	}

}
