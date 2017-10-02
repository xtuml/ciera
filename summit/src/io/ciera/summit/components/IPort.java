package io.ciera.summit.components;

public interface IPort {
	
	public String getID();
	public String getPeerID();
	public void setPeer( IPort peer );
	public void send( IMessage message );
	public void receive( IMessage message );

}
