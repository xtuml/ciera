package io.ciera.runtime.time;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.time.Timer;
import io.ciera.runtime.api.types.Duration;
import io.ciera.runtime.api.types.UniqueId;
import io.ciera.runtime.application.BaseApplication;
import io.ciera.runtime.application.task.Task;
import io.ciera.runtime.application.task.TimerExpiration;

public class EventTimer extends AbstractTimer implements Timer {

    private static final long serialVersionUID = 1L;

    private final String domainName;
    private final Event event;
    private final UniqueId targetId;
    private transient EventTarget target;

    public EventTimer(ExecutionContext context, Event event, EventTarget target) {
        this(UniqueId.random(), context, event, target, Duration.ZERO);
    }

    public EventTimer(ExecutionContext context, Event event, EventTarget target, Duration period) {
        this(UniqueId.random(), context, event, target, period);
    }

    public EventTimer(UniqueId timerHandle, ExecutionContext context, Event event, EventTarget target,
            Duration period) {
        super(timerHandle, context, period);
        this.domainName = target.getDomain().getName();
        this.event = event;
        this.target = target;
        this.targetId = target.getTargetId();
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
            target = BaseApplication.provider().getDomain(domainName).getEventTarget(targetId);
        }
        return target;
    }

    @Override
    public Domain getDomain() {
        return getTarget().getDomain();
    }

}
