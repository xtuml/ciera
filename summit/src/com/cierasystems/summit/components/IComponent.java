package com.cierasystems.summit.components;

import com.cierasystems.summit.classes.IInstancePopulation;
import com.cierasystems.summit.exceptions.XtumlException;

public interface IComponent extends IInstancePopulation {
    
    public void initialize() throws XtumlException;
    public void satisfy( String portName, IPort foreignPort );
	public IPort getPort( String portName );

}
