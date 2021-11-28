package io.ciera.runtime.domain;

import io.ciera.runtime.action.ActionHome;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;

public abstract class Utility implements ActionHome {

    private final Domain domain;
    private final Logger logger;

    public Utility(Domain domain) {
        this.domain = domain;
        this.logger = domain.getLogger();
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    @Override
    public ExecutionContext getContext() {
        return getDomain().getContext();
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

}
