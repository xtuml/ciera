package ciera.statemachine;

import ciera.exceptions.XtumlException;

public interface EventTarget {
    
    public void transition( Event e ) throws XtumlException;

}
