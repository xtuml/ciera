package io.ciera.runtime.domain;

import io.ciera.runtime.action.ActionHome;
import io.ciera.runtime.application.ExecutionContext;

public abstract class Utility implements ActionHome {

    private final Domain domain;

    public Utility(Domain domain) {
        this.domain = domain;
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
    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(), getDomain());
    }

}