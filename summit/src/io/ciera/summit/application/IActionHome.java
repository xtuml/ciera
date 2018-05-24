package io.ciera.summit.application;

import io.ciera.summit.components.IComponent;

public interface IActionHome<C extends IComponent<C>> {

    public IRunContext getRunContext();
    public C context();

    default public void warn( String message ) {
        getRunContext().getExceptionHandler().warn( message );
    }

}
