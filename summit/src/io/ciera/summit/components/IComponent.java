package io.ciera.summit.components;

import io.ciera.summit.application.IRunner;
import io.ciera.summit.classes.IInstancePopulation;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.interfaces.IPort;

public interface IComponent extends IInstancePopulation, IRunner {
    
    public void initialize() throws XtumlException;
    public void satisfy( IPort requiredPort, IPort providedPort );

}
