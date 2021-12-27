package io.ciera.runtime.action;

import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;
import io.ciera.runtime.domain.Domain;

public abstract class DynamicActionHome<R,D extends Domain> implements ActionHome {
    
    private ActionHome actionHome;
    
    public DynamicActionHome(ActionHome actionHome) {
        this.actionHome = actionHome;
    }
    
    public abstract R runAction();

    @Override
    @SuppressWarnings("unchecked")
    public D getDomain() {
        return (D) actionHome.getDomain();
    }

    @Override
    public ExecutionContext getContext() {
        return actionHome.getContext();
    }

    @Override
    public Logger getLogger() {
        return actionHome.getLogger();
    }

}
