package io.ciera.cairn.application;

import io.ciera.summit.application.IActionHome;
import io.ciera.summit.application.IRunContext;
import io.ciera.summit.components.IComponent;

public class ActionHome<C extends IComponent<C>> implements IActionHome<C> {
    
    private C context;
    
    public ActionHome( C context ) {
        this.context = context;
    }

    @Override
    public IRunContext getRunContext() {
        return context.getRunContext();
    }

    @Override
    public C context() {
        return context;
    }

}
