package io.ciera.runtime.time;

import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.time.Timer;
import io.ciera.runtime.api.types.Duration;
import io.ciera.runtime.api.types.UniqueId;
import io.ciera.runtime.application.task.Task;
import io.ciera.runtime.application.task.TimerExpiration;

public class EventTimer extends AbstractTimer implements Timer {

  private static final long serialVersionUID = 1L;

  private final String domainName;
  private final Event event;
  private final UniqueId targetId;
  private transient EventTarget target;

  public EventTimer(final ExecutionContext context, final Event event, final EventTarget target) {
    this(UniqueId.random(), context, event, target, Duration.ZERO);
  }

  public EventTimer(
      final ExecutionContext context,
      final Event event,
      final EventTarget target,
      final Duration period) {
    this(UniqueId.random(), context, event, target, period);
  }

  public EventTimer(
      final UniqueId timerHandle,
      final ExecutionContext context,
      final Event event,
      final EventTarget target,
      final Duration period) {
    super(timerHandle, context, period);
    domainName = target.getDomain().getName();
    this.event = event;
    this.target = target;
    targetId = target.getTargetId();
  }

  @Override
  protected Task getAction() {
    return new TimerExpiration(event, getTarget());
  }

  public Event getEvent() {
    return event;
  }

  public EventTarget getTarget() {
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