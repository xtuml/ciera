package io.ciera.runtime.application.task;

import io.ciera.runtime.api.application.MessageTarget;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.Message;
import io.ciera.runtime.api.types.UniqueId;
import io.ciera.runtime.application.BaseApplication;

public class ReceivedMessage extends Task {

    private static final long serialVersionUID = 1L;

    private final Class<? extends Domain> domainClass;
    private final Message message;
    private final UniqueId targetId;
    private transient MessageTarget target;

    public ReceivedMessage(Message message, MessageTarget target) {
        this.domainClass = target.getDomain().getClass();
        this.message = message;
        this.target = target;
        this.targetId = target.getTargetId();
    }

    @Override
    public void run() {
        if (target == null) {
            target = BaseApplication.getInstance().getDomain(domainClass).getMessageTarget(targetId);
        }
        target.deliver(message);
    }

}
