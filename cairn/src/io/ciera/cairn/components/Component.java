package io.ciera.cairn.components;

import io.ciera.cairn.classes.InstancePopulation;
import io.ciera.summit.components.IComponent;
import io.ciera.summit.interfaces.IPort;

public abstract class Component<C extends IComponent<C>> extends InstancePopulation implements IComponent<C> {

	@Override
	public void satisfy( IPort requiredPort, IPort providedPort ) {
		if ( requiredPort.getPeers().size() < 1 ) requiredPort.addPeer( providedPort );
		providedPort.addPeer( requiredPort );
	}

}
