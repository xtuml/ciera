package io.ciera.runtime.api.exceptions;

import io.ciera.runtime.api.application.MessageTarget;
import io.ciera.runtime.api.domain.Message;

public class MessageTargetException extends RuntimeException {

  private static final long serialVersionUID = 1l;

  private final MessageTarget target;
  private final Message receivedMessage;

  public MessageTargetException(String message, MessageTarget target, Message receivedMessage) {
    this(message, null, target, receivedMessage);
  }

  public MessageTargetException(
      String message, Throwable cause, MessageTarget target, Message receivedMessage) {
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
    return super.getMessage()
        + ": [target="
        + target
        + ", receivedMessage="
        + receivedMessage
        + "]";
  }
}
