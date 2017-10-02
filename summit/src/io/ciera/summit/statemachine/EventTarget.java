package io.ciera.summit.statemachine;

import io.ciera.summit.exceptions.XtumlException;

public interface EventTarget {
    
    public void transition( Event e ) throws XtumlException;

}
