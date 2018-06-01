package io.ciera.cairn.interfaces;

import io.ciera.cairn.application.tasks.ReceivedMessageTask;
import io.ciera.summit.application.IRunContext;
import io.ciera.summit.components.IComponent;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.interfaces.IMessage;
import io.ciera.summit.interfaces.IPort;

public abstract class Port<C extends IComponent<C>> implements IPort<C> {

    private C context;
    private IPort<?> peer;

    public Port( C context, IPort<?> peer ) {
        this.context = context;
        this.peer = peer;
    }
    
    public void send( IMessage message ) {
        if ( null != peer ) {
            peer.getRunContext().execute( new ReceivedMessageTask() {
                @Override
                public void run() throws XtumlException {
                    peer.deliver( message );
                }
            });
        }
    }
    
    @Override
    public void satisfy( IPort<?> peer ) {
        this.peer = peer;
    }
    
    @Override
    public boolean satisfied() {
        return null != peer;
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