package io.ciera.runtime.summit.statemachine;

import io.ciera.runtime.summit.exceptions.XtumlException;

public interface IEventTarget {

    public void accept(IEvent event) throws XtumlException;
    public int getCurrentState() throws XtumlException;

}
