package com.cierasystems.summit.statemachine;

import com.cierasystems.summit.exceptions.XtumlException;

public interface EventTarget {
    
    public void transition( Event e ) throws XtumlException;

}
