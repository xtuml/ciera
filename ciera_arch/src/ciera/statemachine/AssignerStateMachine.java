package ciera.statemachine;

public abstract class AssignerStateMachine extends StateMachine implements EventTarget {
    
    protected EventDispatch dispatch;
    
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
    public void run() {
        dispatch.run();
    }
    
}
