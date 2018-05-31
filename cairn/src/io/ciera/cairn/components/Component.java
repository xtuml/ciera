package io.ciera.cairn.components;

import io.ciera.summit.application.IRunContext;
import io.ciera.summit.components.IComponent;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.IXtumlType;

public abstract class Component<C extends IComponent<C>> implements IComponent<C> {

    private IRunContext runContext;

    public Component( IRunContext runContext ) {
        this.runContext = runContext;
    }

    @Override
    public IRunContext getRunContext() {
        return runContext;
    }

    @Override
    public boolean equality( IXtumlType<C> value ) throws XtumlException {
        return equals( value );
    }

    @Override
    public C defaultValue() {
        return null;
    }

}
