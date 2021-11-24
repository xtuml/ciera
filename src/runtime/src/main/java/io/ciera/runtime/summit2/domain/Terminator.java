package io.ciera.runtime.summit2.domain;

import io.ciera.runtime.summit2.action.ActionHome;
import io.ciera.runtime.summit2.application.ExecutionContext;
import io.ciera.runtime.summit2.application.Logger;
import io.ciera.runtime.summit2.application.task.ReceivedMessage;

public abstract class Terminator implements ActionHome {

    private String name;
    private Domain domain;
    private ExecutionContext context;
    private Logger logger;
    private Terminator peer;

    public Terminator(String name, Domain domain, ExecutionContext context, Logger logger) {
        this.name = name;
        this.domain = domain;
        this.context = context;
        this.logger = logger;
        this.peer = null;
    }

    public void send(Message message) {
        if (peer != null) {
            peer.getContext().addTask(new ReceivedMessage(peer.getContext(), peer, message));
        }
    }

    public abstract void deliver(Message message);

    public void setPeer(Terminator peer) {
        this.peer = peer;
    }

    public String getName() {
        return name;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    @Override
    public ExecutionContext getContext() {
        return context;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

}
