package io.ciera.runtime.application.task;

import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.application.MessageTarget;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.Message;

public class ReceivedMessage extends Task implements DomainTask {

  private static final long serialVersionUID = 1L;

  private final String domainName;
  private final Message message;
  private final Class<? extends MessageTarget> targetClass;
  private transient MessageTarget target;

  public ReceivedMessage(final Message message, final MessageTarget target) {
    domainName = target.getDomain().getName();
    this.message = message;
    this.target = target;
    targetClass = target.getClass();
  }

  @Override
  public void run() {
    getTarget().deliver(message);
  }

  private MessageTarget getTarget() {
    if (target == null) {
      target = Application.getInstance().getDomain(domainName).getMessageTarget(targetClass);
    }
    return target;
  }

  @Override
  public Domain getDomain() {
    return getTarget().getDomain();
  }
}
