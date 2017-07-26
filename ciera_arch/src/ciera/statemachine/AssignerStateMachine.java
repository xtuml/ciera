package ciera.statemachine;

import ciera.application.ApplicationThread;

public abstract class AssignerStateMachine extends StateMachine implements EventTarget {
    
    protected EventDispatch dispatch;
    private ApplicationThread thread;
    private String keyLetters;
    
    public AssignerStateMachine( String keyLetters ) {
        getDefaultThread().addTarget( this );
        dispatch = new EventDispatch();
        this.keyLetters = keyLetters;
    }
    
    @Override
    public void generateTo( Event e ) {
        e.setTarget( this );
        dispatch.generateTo(e);
    }
    
    @Override
    public void generateToSelf( Event e ) {
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
    
    public abstract ApplicationThread getDefaultThread();

    @Override
    public void run() {
        dispatch.run();
    }

    @Override
    public String getKeyLetters() {
        return keyLetters;
    }
    
}
