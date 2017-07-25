package ciera.classes;

import java.util.UUID;

import ciera.application.ApplicationThread;
import ciera.exceptions.EmptyInstanceException;
import ciera.exceptions.XtumlException;
import ciera.statemachine.AssignerStateMachine;
import ciera.statemachine.Event;
import ciera.statemachine.EventDispatch;
import ciera.statemachine.EventTarget;
import ciera.statemachine.InstanceStateMachine;

public abstract class ModelInstance implements EventTarget {
    
    private UUID instanceId;
    private boolean alive;

    private EventDispatch dispatch;
    private ApplicationThread thread;
    private InstancePopulation context;

    private InstanceStateMachine ism;
    private static AssignerStateMachine asm;
    
    // constructors
    public ModelInstance() {
        instanceId = UUID.randomUUID();
        getDefaultThread().addTarget( this );
        alive = true;
        dispatch = new EventDispatch();
        context = null;
        ism = null;
    }
    
    public ModelInstance( InstanceStateMachine ism ) {
        this();
        this.ism = ism;
        ism.setInstance( this );
    }

    public abstract int getClassId();
    public abstract String getKeyLetters();
    
    public UUID getInstanceId() {
        return instanceId;
    }
    
    public void checkLiving() throws XtumlException {
        if ( !alive ) throw new EmptyInstanceException( "Access of deleted instance " );
    }
    
    @Override
    public void transition( Event e ) throws XtumlException {
        checkLiving();
        ism.transition(e);
    }
    
    @Override
    public void generateTo( Event e ) throws XtumlException {
        checkLiving();
        e.setTarget( this );
        dispatch.generateTo( e );
    }

    @Override
    public void generateToSelf( Event e ) throws XtumlException {
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
    
    public void delete() throws XtumlException {
        checkLiving();
        getDefaultThread().removeTarget( this );
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

}
