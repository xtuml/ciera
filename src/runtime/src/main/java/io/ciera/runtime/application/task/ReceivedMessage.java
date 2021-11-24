package io.ciera.runtime.application.task;

import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Task;
import io.ciera.runtime.domain.Message;
import io.ciera.runtime.domain.Terminator;

public class ReceivedMessage extends Task {

    private Terminator target;
    private Message message;

    public ReceivedMessage(ExecutionContext context, Terminator target, Message message) {
        super(context);
        this.target = target;
        this.message = message;
    }

    @Override
    public void run() {
        target.deliver(message);
        getContext().getInstancePopulation().removeMessage(message.getMessageHandle());
    }

}
