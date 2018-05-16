package io.ciera.cairn.components;

import io.ciera.cairn.classes.InstancePopulation;
import io.ciera.summit.application.IRunContext;
import io.ciera.summit.components.IComponent;
import io.ciera.summit.interfaces.IPort;

public abstract class Component extends InstancePopulation implements IComponent {

    private IRunContext runContext;

	public Component( IRunContext runContext ) {
		this.runContext = runContext;
	}

	@Override
	public void satisfy( IPort requiredPort, IPort providedPort ) {
		if ( requiredPort.getPeers().size() < 1 ) requiredPort.addPeer( providedPort );
		providedPort.addPeer( requiredPort );
	}

	@Override
	public IRunContext getRunContext() {
		return runContext;
	}

}
