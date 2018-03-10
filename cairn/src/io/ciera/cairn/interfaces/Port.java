package io.ciera.cairn.interfaces;

import java.util.HashSet;
import java.util.Set;

import io.ciera.summit.interfaces.IPort;

public abstract class Port implements IPort {
	
	private Set<IPort> peers;
	
	public Port() {
		peers = new HashSet<>();
	}

	@Override
	public void addPeer( IPort peer ) {
		peers.add( peer );
	}

	@Override
	public Set<IPort> getPeers() {
		return peers;
	}

}
