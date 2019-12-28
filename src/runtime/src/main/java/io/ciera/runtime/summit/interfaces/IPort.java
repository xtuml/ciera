package io.ciera.runtime.summit.interfaces;

import io.ciera.runtime.summit.application.IActionHome;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;

public interface IPort<C extends IComponent<C>> extends IActionHome<C> {

    public void deliver(IMessage message) throws XtumlException;

    public void satisfy(IPort<?> peer);

    public boolean satisfied();
    
    public String getName();
    
    public String getPeerName();
    
    public int getPeerId();

}
