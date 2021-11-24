package io.ciera.runtime.action;

import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;
import io.ciera.runtime.domain.Domain;

/**
 * An action home represents an element that can contain generated action
 * language statements. The action home provides a standard way to access the
 * domain (for the purpose of accessing domain resources such as relators,
 * terminator messages, utilities, etc). It also gives access to the execution
 * context for time and event generation.
 */
public interface ActionHome {

    /**
     * Get the parent domain.
     * 
     * @return {@link Domain} the parent domain within which the actions are
     *         defined.
     */
    public Domain getDomain();

    /**
     * Get the execution context.
     * 
     * @return {@link ExecutionContext} the execution context within which the
     *         actions run.
     */
    public ExecutionContext getContext();

    /**
     * Get the logger instance.
     * 
     * @return {@link Logger} the logger for this application.
     */
    public Logger getLogger();

}
