package ciera.classes;

import java.util.UUID;

import ciera.application.ApplicationThread;
import ciera.classes.exceptions.EmptyInstanceException;
import ciera.classes.exceptions.ModelIntegrityException;
import ciera.statemachine.AssignerStateMachine;
import ciera.statemachine.Event;
import ciera.statemachine.EventDispatch;
import ciera.statemachine.EventTarget;
import ciera.statemachine.InstanceStateMachine;
import ciera.statemachine.exceptions.StateMachineException;

public abstract class ModelInstance implements EventTarget {
    
    private UUID instanceId;
    private boolean alive;

    private EventDispatch dispatch;
    private ApplicationThread thread;
    private InstancePopulation context;

    protected InstanceStateMachine ism;
    protected AssignerStateMachine asm;
    
    public ModelInstance() {
        instanceId = UUID.randomUUID();
        getDefaultThread().addTarget( this );
        alive = true;
    }

    public abstract int getClassId();
    public abstract String getKeyLetters();
    
    public UUID getInstanceId() {
        return instanceId;
    }
    
    public void checkLiving() throws EmptyInstanceException {
        if ( !alive ) throw new EmptyInstanceException( "Access of deleted instance " );
    }
    
    @Override
    public void transition( Event e ) throws StateMachineException, EmptyInstanceException, ModelIntegrityException {
        checkLiving();
        ism.transition(e);
    }
    
    @Override
    public void generateTo( Event e ) throws EmptyInstanceException {
        checkLiving();
        e.setTarget( this );
        dispatch.generateTo( e );
    }

    @Override
    public void generateToSelf( Event e ) throws EmptyInstanceException {
        e.setToSelf( true );
        generateTo( e );
    }
    
    @Override
    public ApplicationThread getThread() {
        return thread;
    }
    
    @Override
    public void setThread( ApplicationThread thread ) {
        this.thread = thread;
    }
    
    @Override
    public void run() {
        dispatch.run();
    }
    
    public InstancePopulation getContext() {
        return context;
    }
    
    public void setContext( InstancePopulation context ) {
        this.context = context;
    }
    
    public void delete() throws EmptyInstanceException {
        checkLiving();
        getDefaultThread().removeTarget( this );
        alive = false;
    }
    
    @Override
    public boolean equals( Object obj ) {
        if ( !(obj instanceof ModelInstance ) ) return false;
        return ((ModelInstance)obj).getInstanceId().equals(instanceId);
    }

}
