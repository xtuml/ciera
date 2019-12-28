package io.ciera.runtime.summit.statemachine;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;

public interface IEventTarget {

    public void accept(IEvent event) throws XtumlException;
    public int getCurrentState() throws XtumlException;
    public UniqueId getInstanceId();
    public String getKeyLetters();

}
