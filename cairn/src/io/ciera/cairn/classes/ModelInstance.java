package io.ciera.cairn.classes;

import io.ciera.summit.classes.IEmptyInstance;
import io.ciera.summit.classes.IInstancePopulation;
import io.ciera.summit.classes.IInstanceSet;
import io.ciera.summit.classes.IModelInstance;
import io.ciera.summit.exceptions.DeletedInstanceException;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.statemachine.ClassStateMachine;
import io.ciera.summit.statemachine.Event;
import io.ciera.summit.statemachine.InstanceStateMachine;
import io.ciera.summit.util.UniqueId;

public abstract class ModelInstance implements IModelInstance {
    
    // empty instance
    public static final ModelInstance emptyModelInstance = new EmptyModelInstance();

    private UniqueId instanceId;

    private IInstancePopulation context;

    private InstanceStateMachine ism;
    private static ClassStateMachine csm;
    
    // constructors
    public ModelInstance() {
        instanceId = new UniqueId();
        context = null;
        ism = null;
    }
    
    @Override
    public UniqueId getInstanceId() {
        return instanceId;
    }
    
    @Override
    public void checkLiving() throws XtumlException {
        if ( instanceId.isNull() ) throw new DeletedInstanceException( "Access of deleted instance " );
    }
    
    @Override
    public void transition( Event e ) throws XtumlException {
        checkLiving();
        ism.transition(e);
    }
    
    @Override
    public IInstancePopulation getContext() {
        return context;
    }
    
    @Override
    public void setContext( IInstancePopulation context ) {
        this.context = context;
    }
    
    @Override
    public void delete() throws XtumlException {
        checkLiving();
        instanceId.nullify();
    }

    public static ClassStateMachine getAsm() {
        return csm;
    }
    
    @Override
    public boolean equals( Object obj ) {
        if ( !(obj instanceof ModelInstance ) ) return false;
        return ((ModelInstance)obj).getInstanceId().equals(instanceId);
    }
    
    @Override
    public int compareTo( IModelInstance instance ) {
        return instanceId.compareTo( instance.getInstanceId() );
    }

}

class EmptyModelInstance extends ModelInstance implements IEmptyInstance {

    @Override
    public int getClassNumber() {
        return 0;
    }

    @Override
    public String getKeyLetters() {
        return null;
    }

	@Override
	public IInstanceSet toSet() {
		return null;
	}

}
