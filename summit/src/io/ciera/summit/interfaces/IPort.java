package io.ciera.summit.interfaces;

import io.ciera.summit.application.IActionHome;
import io.ciera.summit.components.IComponent;
import io.ciera.summit.exceptions.XtumlException;

public interface IPort<C extends IComponent<C>> extends IActionHome<C> {

    public void deliver( IMessage message ) throws XtumlException;

}
