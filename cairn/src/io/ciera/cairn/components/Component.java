package io.ciera.cairn.components;

import java.util.HashMap;
import java.util.Map;
import io.ciera.cairn.classes.InstancePopulation;
import io.ciera.summit.components.IComponent;
import io.ciera.summit.components.IPort;

public abstract class Component extends InstancePopulation implements IComponent {
	
	private Map<String, IPort> ports;
    
    public Component( IPort ... ports ) {
        this.ports = new HashMap<String, IPort>();
        for ( IPort port : ports ) {
        	this.ports.put( port.getClass().getName(), port );
        }
    }

	@Override
	public void satisfy( String portName, IPort foreignPort ) {
		IPort port = ports.get( portName );
		if ( null != port ) port.setPeer( foreignPort );
	}
	
	@Override
	public IPort getPort( String portName ) {
		return ports.get( portName );
	}

}
