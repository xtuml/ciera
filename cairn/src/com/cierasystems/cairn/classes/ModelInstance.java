package com.cierasystems.cairn.classes;

import com.cierasystems.summit.classes.IEmptyInstance;
import com.cierasystems.summit.classes.IInstancePopulation;
import com.cierasystems.summit.classes.IModelInstance;
import com.cierasystems.summit.exceptions.DeletedInstanceException;
import com.cierasystems.summit.exceptions.XtumlException;
import com.cierasystems.summit.statemachine.ClassStateMachine;
import com.cierasystems.summit.statemachine.Event;
import com.cierasystems.summit.statemachine.InstanceStateMachine;
import com.cierasystems.summit.util.UniqueId;

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

}
