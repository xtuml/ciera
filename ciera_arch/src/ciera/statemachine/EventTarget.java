package ciera.statemachine;

import ciera.application.ApplicationThread;
import ciera.exceptions.XtumlException;

public interface EventTarget extends Runnable {
    
    public void transition( Event e ) throws XtumlException;
    public void generateTo( Event e ) throws XtumlException;
    public void generateToSelf( Event e ) throws XtumlException;
    public ApplicationThread getThread();
    public void setThread( ApplicationThread thread );
    public ApplicationThread getDefaultThread();

}
