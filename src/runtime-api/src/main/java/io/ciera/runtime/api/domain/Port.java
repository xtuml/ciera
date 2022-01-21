package io.ciera.runtime.api.domain;

import io.ciera.runtime.api.application.MessageTarget;

public interface Port extends MessageTarget {

    public void send(Message message);

    public void setPeer(Port peer);

    public boolean satisfied();

}