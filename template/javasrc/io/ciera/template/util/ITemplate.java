package io.ciera.template.util;

import io.ciera.summit.application.IActionHome;
import io.ciera.summit.components.IComponent;
import io.ciera.summit.exceptions.XtumlException;

public interface ITemplate<C extends IComponent<C>> extends IActionHome<C> {
    
    public void evaluate() throws XtumlException;

}
