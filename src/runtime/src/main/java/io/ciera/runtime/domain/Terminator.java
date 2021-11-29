package io.ciera.runtime.domain;

import io.ciera.runtime.action.ActionHome;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;
import io.ciera.runtime.application.MessageTarget;
import io.ciera.runtime.application.Named;
import io.ciera.runtime.application.task.ReceivedMessage;

public abstract class Terminator implements ActionHome, MessageTarget, Named {

    private final String name;
    private final Domain domain;
    private final Logger logger;
    private ExecutionContext context;
    private MessageTarget peer;

    public Terminator(String name, Domain domain) {
        this.name = name;
        this.domain = domain;
        this.logger = domain.getLogger();
        this.context = null;
        this.peer = null;
    }

    public void send(Message message) {
        if (peer != null) {
            peer.getContext().addTask(new ReceivedMessage(peer.getContext(), message, peer));
        }
    }

    @Override
    public abstract void deliver(Message message);

    public void setPeer(Terminator peer) {
        this.peer = peer;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    @Override
    public ExecutionContext getContext() {
        return context != null ? context : getDomain().getContext();
    }

    @Override
    public void attachTo(ExecutionContext context) {
        this.context = context;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public String toString() {
        return String.format("Terminator[%s]", getName());
    }

}
