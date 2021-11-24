package io.ciera.runtime.summit2.application;

import io.ciera.runtime.summit2.domain.Message;
import io.ciera.runtime.summit2.domain.Terminator;

public class ReceivedMessage extends Task {

    private Terminator target;
    private Message message;

    public ReceivedMessage(Terminator target, Message message) {
        this.target = target;
        this.message = message;
    }

    // TODO

}
