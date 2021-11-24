package io.ciera.runtime.summit2.application.task;

import io.ciera.runtime.summit2.application.ExecutionContext;
import io.ciera.runtime.summit2.application.Task;
import io.ciera.runtime.summit2.domain.Message;
import io.ciera.runtime.summit2.domain.Terminator;

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
