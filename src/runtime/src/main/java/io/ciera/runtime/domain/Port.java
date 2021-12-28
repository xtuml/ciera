package io.ciera.runtime.domain;

import io.ciera.runtime.application.MessageTarget;

public interface Port extends MessageTarget {

    public void send(Message message);

    public void setPeer(Port peer);

    public boolean satisfied();

}
