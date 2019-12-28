package io.ciera.runtime.summit.classes;

import io.ciera.runtime.summit.exceptions.XtumlException;

public interface IInstancePopulation {

    public boolean addInstance(IModelInstance<?, ?> instance) throws XtumlException;

    public boolean removeInstance(IModelInstance<?, ?> instance) throws XtumlException;
    
    public int getId();

}
