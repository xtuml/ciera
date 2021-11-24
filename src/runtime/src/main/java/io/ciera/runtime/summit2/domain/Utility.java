package io.ciera.runtime.summit2.domain;

import io.ciera.runtime.summit2.action.ActionHome;
import io.ciera.runtime.summit2.application.ExecutionContext;
import io.ciera.runtime.summit2.application.Logger;

public abstract class Utility implements ActionHome {

    private Domain domain;
    private ExecutionContext context;
    private Logger logger;

    public Utility(Domain domain, ExecutionContext context, Logger logger) {
        this.domain = domain;
        this.context = context;
        this.logger = logger;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    @Override
    public ExecutionContext getContext() {
        return context;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

}
