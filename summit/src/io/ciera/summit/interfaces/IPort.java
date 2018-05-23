package io.ciera.summit.interfaces;

import java.util.Set;

public interface IPort {

    public void addPeer( IPort peer );
    public Set<IPort> getPeers();
    public void post( IMessage message );
    public IMessage postSync( IMessage message );
    public void deliver( IMessage message );

}
