package io.ciera.cairn.interfaces;

import io.ciera.cairn.application.tasks.ReceivedMessageTask;
import io.ciera.summit.application.IRunContext;
import io.ciera.summit.components.IComponent;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.interfaces.IMessage;
import io.ciera.summit.interfaces.IPort;

public abstract class Port<C extends IComponent<C>> implements IPort<C> {

    private C context;

    public Port( C context ) {
        this.context = context;
    }
    
    public void send( IPort<?> target, IMessage message ) {
        target.getRunContext().execute( new ReceivedMessageTask() {
            @Override
            public void run() throws XtumlException {
                target.deliver( message );
            }
        });
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