package io.ciera.runtime.summit.statemachine;

import io.ciera.runtime.summit.exceptions.XtumlException;

public interface ITransition {

    public int execute(IEvent event) throws XtumlException;

}
