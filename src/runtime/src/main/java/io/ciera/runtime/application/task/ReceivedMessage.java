package io.ciera.runtime.application.task;

import io.ciera.runtime.api.application.MessageTarget;
import io.ciera.runtime.api.domain.Message;

public class ReceivedMessage extends Task {

    private final Message message;
    private final MessageTarget target;

    public ReceivedMessage(Message message, MessageTarget target) {
        this.message = message;
        this.target = target;
    }

    @Override
    public void run() {
        target.deliver(message);
    }

}
