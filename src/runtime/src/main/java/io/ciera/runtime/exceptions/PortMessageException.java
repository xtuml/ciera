package io.ciera.runtime.exceptions;

import io.ciera.runtime.domain.Domain;
import io.ciera.runtime.domain.Message;
import io.ciera.runtime.domain.Port;

public class PortMessageException extends MessageTargetException {

    private static final long serialVersionUID = 1l;
    
    private final Domain domain;
    
    public PortMessageException(String message, Domain domain, Port port, Message receivedMessage) {
        super(message, port, receivedMessage);
        this.domain = domain;
    }
    
    public PortMessageException(String message, Throwable cause, Domain domain, Port port, Message receivedMessage) {
        super(message, cause, port, receivedMessage);
        this.domain = domain;
    }

    public Domain getDomain() {
        return domain;
    }

    public Port getPort() {
        return (Port) super.getTarget();
    }

}
