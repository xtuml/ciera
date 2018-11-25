package io.ciera.runtime.summit.components;

import io.ciera.runtime.IVersioned;
import io.ciera.runtime.instanceloading.IPopulationLoader;
import io.ciera.runtime.summit.application.IActionHome;
import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.classes.IInstancePopulation;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.EventHandle;
import io.ciera.runtime.summit.types.IXtumlType;

public interface IComponent<C extends IComponent<C>>
        extends IInstancePopulation, IActionHome<C>, IXtumlType<C>, IVersioned {

    public void initialize() throws XtumlException;

    public void generate(EventHandle event) throws XtumlException;
    
    public IApplication getApp();
    
    public void addLoader(String key, IPopulationLoader loader);

    public IPopulationLoader getLoader(String key);

    public IPopulationLoader getDefaultLoader();

}
