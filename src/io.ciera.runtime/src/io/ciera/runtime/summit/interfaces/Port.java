package io.ciera.runtime.summit.interfaces;

import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.application.tasks.ReceivedMessageTask;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;

public abstract class Port<C extends IComponent<C>> implements IPort<C> {

    private C context;
    private IPort<?> peer;

    public Port(C context, IPort<?> peer) {
        this.context = context;
        this.peer = peer;
    }

    public void send(IMessage message) throws XtumlException {
        if (null != peer) {
            peer.getRunContext().execute(new ReceivedMessageTask() {
                @Override
                public void run() throws XtumlException {
                    peer.deliver(message);
                }
            });
        }
    }

    @Override
    public void satisfy(IPort<?> peer) {
        this.peer = peer;
    }

    @Override
    public boolean satisfied() {
        return null != peer;
    }
    
    @Override
    public String getPeerName() {
    	if (satisfied()) {
    		return peer.getName();
    	}
    	else return "";
    }
    
    @Override
    public int getPeerId() {
    	if (satisfied()) {
    		return peer.context().getId();
    	}
    	else return -1;
    }

    @Override
    public IRunContext getRunContext() {
        return context().getRunContext();
    }

    @Override
    public C context() {
        return context;
    }

}