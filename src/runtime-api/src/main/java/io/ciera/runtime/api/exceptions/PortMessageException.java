package io.ciera.runtime.api.exceptions;

import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.Message;
import io.ciera.runtime.api.domain.Port;

public class PortMessageException extends MessageTargetException {

    private static final long serialVersionUID = 1l;

    private final Domain domain;

    public PortMessageException(String message, Domain domain, Port port, Message receivedMessage) {
        this(message, null, domain, port, receivedMessage);
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

    @Override
    public String getMessage() {
        return super.getOriginalMessage() + ": [domain=" + domain + ", port=" + getPort() + ", receivedMessage="
                + getReceivedMessage() + "]";
    }

}
