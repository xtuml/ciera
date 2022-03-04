package io.ciera.runtime.domain;

import io.ciera.runtime.api.action.ActionHome;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.application.MessageTarget;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.Message;
import io.ciera.runtime.api.domain.Port;
import io.ciera.runtime.api.exceptions.PortMessageException;
import io.ciera.runtime.application.task.ReceivedMessage;

public abstract class AbstractPort implements Port, ActionHome {

    private final String name;
    private final Domain domain;
    private MessageTarget peer;

    public AbstractPort(String name, Domain domain) {
        this.name = name;
        this.domain = domain;
        this.peer = null;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void send(Message message) {
        if (peer != null) {
            peer.getContext().execute(new ReceivedMessage(message, peer));
        }
    }

    public void runMessageHandler(Message receivedMessage, Runnable messageHandler) {
        try {
            messageHandler.run();
        } catch (RuntimeException e) {
            throw new PortMessageException("Exception in state machine action", e, getDomain(), this, receivedMessage);
        }
    }

    @Override
    public void deliver(Message message) {
        throw new PortMessageException("Port does not implement any incoming message types", getDomain(), this, null);
    }

    @Override
    public void setPeer(MessageTarget peer) {
        this.peer = peer;
    }

    @Override
    public MessageTarget getPeer() {
        return peer;
    }

    @Override
    public boolean satisfied() {
        return peer != null;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    @Override
    public ExecutionContext getContext() {
        return domain.getContext();
    }

    @Override
    public String toString() {
        return String.format("Port[%s]", name);
    }

}
