package io.ciera.runtime.summit2.domain;

import io.ciera.runtime.summit2.action.ActionHome;
import io.ciera.runtime.summit2.application.Application;
import io.ciera.runtime.summit2.application.ExecutionContext;
import io.ciera.runtime.summit2.application.Logger;

/**
 * A domain is a composite of translated model elements including classes,
 * relationships, types, functions, etc. The component provides access to
 * outbound (required) interface messages and the instance population for every
 * action within it.
 */
public abstract class Domain implements ActionHome {

    private String name;
    private Application application;

    public Domain(String name, Application application) {
        this.name = name;
        this.application = application;
    }

    /**
     * Execute application level initialization functions.
     */
    public abstract void initialize();

    /**
     * Get the deploying application for this domain.
     * 
     * @return The {@link Application} instance.
     */
    public Application getApplication() {
        return application;
    }

    @Override
    public Domain getDomain() {
        return this;
    }

    @Override
    public ExecutionContext getContext() {
        return application.getContextFor(this);
    }

    @Override
    public Logger getLogger() {
        return application.getLogger();
    }

    @Override
    public String toString() {
        return String.format("DOMAIN[%s]", name);
    }

}
