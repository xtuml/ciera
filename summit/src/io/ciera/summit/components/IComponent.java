package io.ciera.summit.components;

import io.ciera.summit.classes.IInstancePopulation;
import io.ciera.summit.exceptions.XtumlException;

public interface IComponent extends IInstancePopulation {
    
    public void initialize() throws XtumlException;
    public void satisfy( String portName, IPort foreignPort );
	public IPort getPort( String portName );

}
