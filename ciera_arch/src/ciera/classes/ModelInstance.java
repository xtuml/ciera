package ciera.classes;

import java.util.UUID;

import ciera.exceptions.DeletedInstanceException;
import ciera.exceptions.XtumlException;
import ciera.statemachine.AssignerStateMachine;
import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;
import ciera.statemachine.InstanceStateMachine;

public abstract class ModelInstance implements EventTarget, Comparable<ModelInstance> {
    
    // empty instance
    public static final ModelInstance emptyInstance = new EmptyModelInstance();

    private UUID instanceId;
    private boolean alive;

    private InstancePopulation context;

    private InstanceStateMachine ism;
    private static AssignerStateMachine asm;
    
    // constructors
    public ModelInstance() {
        instanceId = UUID.randomUUID();
        alive = true;
        context = null;
        ism = null;
    }
    
    public ModelInstance( InstanceStateMachine ism ) {
        this();
        this.ism = ism;
        ism.setInstance( this );
    }

    public abstract int getClassNumber();
    public abstract String getKeyLetters();
    
    public UUID getInstanceId() {
        return instanceId;
    }
    
    public void checkLiving() throws XtumlException {
        if ( !alive ) throw new DeletedInstanceException( "Access of deleted instance " );
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
        alive = false;
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