package io.ciera.runtime;

import io.ciera.runtime.api.Domain;
import io.ciera.runtime.api.Message;
import io.ciera.runtime.api.MessageTarget;
import io.ciera.runtime.api.Port;
import io.ciera.runtime.api.exceptions.PortMessageException;

public abstract class AbstractPort implements Port {

  // TODO dependencies
  private final String name = null;
  private final Domain domain = null;

  private MessageTarget peer = null;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void send(final Message message) {
    if (peer != null) {
      // TODO
      // peer.getContext().execute(new ReceivedMessage(message, peer));
    }
  }

  public void runMessageHandler(final Message receivedMessage, final Runnable messageHandler) {
    try {
      messageHandler.run();
    } catch (final RuntimeException e) {
      throw new PortMessageException(
          "Exception in state machine action", e, getDomain(), this, receivedMessage);
    }
  }

  @Override
  public void deliver(final Message message) {
    throw new PortMessageException(
        "Port does not implement any incoming message types", getDomain(), this, null);
  }

  @Override
  public void setPeer(final MessageTarget peer) {
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
  public Domain getDomain(final String domainName) {
    return null; // TODO
  }

  @Override
  public String toString() {
    return String.format("Port[%s]", name);
  }
}
