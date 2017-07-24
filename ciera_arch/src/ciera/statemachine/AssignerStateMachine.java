package ciera.statemachine;

public abstract class AssignerStateMachine extends StateMachine implements EventTarget {
    
    protected EventDispatch dispatch;
    
    public void generateTo( Event e ) {
        dispatch.generateTo(e);
    }
    
}
