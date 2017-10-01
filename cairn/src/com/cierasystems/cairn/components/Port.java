package com.cierasystems.cairn.components;

import com.cierasystems.summit.components.IMessage;
import com.cierasystems.summit.components.IPort;

public abstract class Port implements IPort {
	
	private IPort peer;
	private String id;

	@Override
	public void send( IMessage message ) {
		if ( null != peer ) peer.receive( message );
	}

	@Override
	public String getID() {
		return id;
	}

	@Override
	public String getPeerID() {
		if ( null != peer ) return peer.getID();
		return null;
	}

	@Override
	public void setPeer( IPort peer ) {
		this.peer = peer;
	}

}
