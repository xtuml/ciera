package io.ciera.runtime.exceptions;

import io.ciera.runtime.application.MessageTarget;
import io.ciera.runtime.domain.Message;

public class MessageTargetException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    private final MessageTarget target;
    private final Message receivedMessage;

    public MessageTargetException(String message, MessageTarget target, Message receivedMessage) {
        super(message);
        this.target = target;
        this.receivedMessage = receivedMessage;
    }

    public MessageTargetException(String message, Throwable cause, MessageTarget target, Message receivedMessage) {
        super(message, cause);
        this.target = target;
        this.receivedMessage = receivedMessage;
    }

    public MessageTarget getTarget() {
        return target;
    }

    public Message getReceivedMessage() {
        return receivedMessage;
    }

    public String getOriginalMessage() {
        return super.getMessage();
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ": [target=" + target + ", receivedMessage=" + receivedMessage + "]";
    }

}
