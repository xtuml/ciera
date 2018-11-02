package io.ciera.runtime.summit.components;

import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.exceptions.XtumlException;

public abstract class Component<C extends IComponent<C>> implements IComponent<C> {

    private IRunContext runContext;

    public Component() {
        runContext = null;
    }

    public Component( IRunContext runContext ) {
        this.runContext = runContext;
    }

    @Override
    public IRunContext getRunContext() {
        return runContext;
    }

    @Override
    public boolean equality( C value ) throws XtumlException {
        return equals( value );
    }

}
