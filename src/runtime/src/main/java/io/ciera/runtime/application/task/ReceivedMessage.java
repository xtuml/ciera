package io.ciera.runtime.application.task;

import io.ciera.runtime.api.application.MessageTarget;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.Message;
import io.ciera.runtime.application.BaseApplication;

public class ReceivedMessage extends Task implements DomainTask {

    private static final long serialVersionUID = 1L;

    private final Class<? extends Domain> domainClass;
    private final Message message;
    private final Class<? extends MessageTarget> targetClass;
    private transient MessageTarget target;

    public ReceivedMessage(Message message, MessageTarget target) {
        this.domainClass = target.getDomain().getClass();
        this.message = message;
        this.target = target;
        this.targetClass = target.getClass();
    }

    @Override
    public void run() {
        getTarget().deliver(message);
    }

    private MessageTarget getTarget() {
        if (target == null) {
            target = BaseApplication.getInstance().getDomain(domainClass).getMessageTarget(targetClass);
        }
        return target;
    }

    @Override
    public Domain getDomain() {
        return getTarget().getDomain();
    }

}
