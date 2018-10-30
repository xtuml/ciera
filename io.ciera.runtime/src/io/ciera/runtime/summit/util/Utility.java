package io.ciera.runtime.summit.util;

import io.ciera.summit.application.IActionHome;
import io.ciera.summit.application.IRunContext;
import io.ciera.summit.components.IComponent;

public class Utility<C extends IComponent<C>> implements IActionHome<C> {

    private C context;

    public Utility( C context ) {
        this.context = context;
    }

    @Override
    public IRunContext getRunContext() {
        return context().getRunContext();
    }

    @Override
    public C context() {
        return context;
    }

}