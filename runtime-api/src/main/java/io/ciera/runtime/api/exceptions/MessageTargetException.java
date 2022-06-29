package io.ciera.runtime.api.exceptions;

import io.ciera.runtime.api.application.MessageTarget;
import io.ciera.runtime.api.domain.Message;

public class MessageTargetException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final MessageTarget target;
  private final Message receivedMessage;

  public MessageTargetException(
      final String message, final MessageTarget target, final Message receivedMessage) {
    this(message, null, target, receivedMessage);
  }

  public MessageTargetException(
      final String message,
      final Throwable cause,
      final MessageTarget target,
      final Message receivedMessage) {
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
