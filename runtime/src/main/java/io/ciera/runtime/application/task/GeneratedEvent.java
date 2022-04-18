package io.ciera.runtime.application.task;

import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.application.ExecutionContext.ExecutionMode;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.types.UniqueId;

public class GeneratedEvent extends Task implements DomainTask {

  private static final long serialVersionUID = 1L;

  private final String domainName;
  private final Event event;
  private final UniqueId targetId;
  private final ExecutionContext.ExecutionMode executionMode;
  private transient EventTarget target;

  public GeneratedEvent(Event event, EventTarget target) {
    this(event, target, null);
  }

  public GeneratedEvent(
      Event event, EventTarget target, ExecutionContext.ExecutionMode executionMode) {
    this.domainName = target.getDomain().getName();
    this.event = event;
    this.target = target;
    this.targetId = target.getTargetId();
    this.executionMode = executionMode;
  }

  @Override
  public void run() {
    getTarget().consumeEvent(event);
  }

  @Override
  public int getPriority() {
    if (executionMode == ExecutionMode.SEQUENTIAL) {
      return Task.SEQUENTIAL_EVENT_PRIORITY;
    } else {
      return super.getPriority();
    }
  }

  private EventTarget getTarget() {
    if (target == null) {
      target = Application.getInstance().getDomain(domainName).getEventTarget(targetId);
    }
    return target;
  }

  @Override
  public Domain getDomain() {
    return getTarget().getDomain();
  }
}
