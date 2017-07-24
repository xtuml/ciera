package ciera.statemachine;

import ciera.classes.exceptions.EmptyInstanceException;
import ciera.classes.exceptions.ModelIntegrityException;
import ciera.statemachine.exceptions.StateMachineException;

public interface EventTarget extends Runnable {
    
    public void transition( Event e ) throws StateMachineException, EmptyInstanceException, ModelIntegrityException;
    public void generateTo( Event e ) throws EmptyInstanceException;
    public void generateToSelf( Event e ) throws EmptyInstanceException;

}
