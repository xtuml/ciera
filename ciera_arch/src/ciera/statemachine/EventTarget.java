package ciera.statemachine;

import ciera.classes.exceptions.EmptyInstanceException;
import ciera.statemachine.exceptions.StateMachineException;

public interface EventTarget {
    
    public void transition( Event e ) throws StateMachineException, EmptyInstanceException;
    public void generateTo( Event e ) throws EmptyInstanceException;

}
