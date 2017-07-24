package ciera.statemachine;

public abstract class AssignerStateMachine extends StateMachine implements EventTarget {
    
    protected EventDispatch dispatch;
    
    public void generateTo( Event e ) {
        e.setTarget( this );
        dispatch.generateTo(e);
    }
    
    public void generateToSelf( Event e ) {
        e.setToSelf( true );
        generateTo( e );
    }
    
}
