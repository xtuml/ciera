package io.ciera.runtime.summit.util;

import io.ciera.runtime.summit.application.IActionHome;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.components.IComponent;

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
