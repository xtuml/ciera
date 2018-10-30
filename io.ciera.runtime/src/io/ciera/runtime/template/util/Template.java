package io.ciera.runtime.template.util;

import io.ciera.summit.application.IActionHome;
import io.ciera.summit.application.IRunContext;
import io.ciera.summit.components.IComponent;

public abstract class Template<C extends IComponent<C>> implements ITemplate, IActionHome<C> {

    private C population;

    public Template( C population ) {
        this.population = population;   
    }

    @Override              
    public IRunContext getRunContext() {
        return context().getRunContext();
    }

    @Override
    public C context() {    
        return population;
    }

}
