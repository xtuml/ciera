package io.ciera.runtime.summit2.application;

import io.ciera.runtime.summit2.action.ActionHome;

public abstract class Application {

    public abstract Logger getLogger();

    public abstract ExecutionContext getContextFor(ActionHome ah);

}
