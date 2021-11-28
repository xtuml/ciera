package io.ciera.runtime.application.task;

import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.MessageTarget;
import io.ciera.runtime.application.Task;
import io.ciera.runtime.domain.Message;

// TODO this assumes that whoever created the task created this message in this context
public class ReceivedMessage extends Task {

    private final Message message;
    private final MessageTarget target;

    public ReceivedMessage(ExecutionContext context, Message message, MessageTarget target) {
        super(context);
        this.message = message;
        this.target = target;
    }

    @Override
    public void run() {
        target.deliver(message);
        getContext().getInstancePopulation().removeMessage(message.getMessageHandle());
    }

}
