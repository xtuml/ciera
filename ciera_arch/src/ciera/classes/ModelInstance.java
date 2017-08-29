package ciera.classes;

import ciera.exceptions.DeletedInstanceException;
import ciera.exceptions.XtumlException;
import ciera.statemachine.AssignerStateMachine;
import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;
import ciera.statemachine.InstanceStateMachine;
import ciera.util.UniqueId;

public abstract class ModelInstance implements EventTarget, Comparable<ModelInstance> {
    
    // empty instance
    public static final ModelInstance emptyModelInstance = new EmptyModelInstance();

    private UniqueId instanceId;

    private InstancePopulation context;

    private InstanceStateMachine ism;
    private static AssignerStateMachine asm;
    
    // constructors
    public ModelInstance() {
        instanceId = new UniqueId();
        context = null;
        ism = null;
    }
    
    public abstract int getClassNumber();
    public abstract String getKeyLetters();
    
    protected UniqueId getInstanceId() {
        return instanceId;
    }
    
    public void checkLiving() throws XtumlException {
        if ( instanceId.isNull() ) throw new DeletedInstanceException( "Access of deleted instance " );
    }
    
    @Override
    public void transition( Event e ) throws XtumlException {
        checkLiving();
        ism.transition(e);
    }
    
    public InstancePopulation getContext() {
        return context;
    }
    
    public void setContext( InstancePopulation context ) {
        this.context = context;
    }
    
    public void delete() throws XtumlException {
        checkLiving();
        instanceId.nullify();
    }

    public static AssignerStateMachine getAsm() {
        return asm;
    }
    
    @Override
    public boolean equals( Object obj ) {
        if ( !(obj instanceof ModelInstance ) ) return false;
        return ((ModelInstance)obj).getInstanceId().equals(instanceId);
    }
    
    @Override
    public int compareTo( ModelInstance instance ) {
        return instanceId.compareTo( instance.getInstanceId() );
    }

}

class EmptyModelInstance extends ModelInstance implements EmptyInstance {

    @Override
    public int getClassNumber() {
        return 0;
    }

    @Override
    public String getKeyLetters() {
        return null;
    }

}
