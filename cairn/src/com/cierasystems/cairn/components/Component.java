package com.cierasystems.cairn.components;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cierasystems.cairn.classes.InstancePopulation;
import com.cierasystems.summit.classes.IInstanceSet;
import com.cierasystems.summit.components.IComponent;
import com.cierasystems.summit.components.IPort;

public abstract class Component extends InstancePopulation implements IComponent {
	
	private Map<String, IPort> ports;
    
    public Component( IPort ... ports ) {
        Map<Class<?>, IInstanceSet> instancePopulation = new ConcurrentHashMap<Class<?>, IInstanceSet>();
        for ( Class<?> type : getClasses() ) {
            instancePopulation.put( type, getNewInstanceSetForClass( type ) );
        }
        this.setInstancePopulationMap( instancePopulation );
        this.ports = new ConcurrentHashMap<String, IPort>();
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
