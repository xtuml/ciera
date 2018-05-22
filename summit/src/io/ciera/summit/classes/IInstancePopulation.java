package io.ciera.summit.classes;

import io.ciera.summit.exceptions.XtumlException;

public interface IInstancePopulation {

    public boolean addInstance( IModelInstance<?,?> instance ) throws XtumlException;
    public boolean removeInstance( IModelInstance<?,?> instance ) throws XtumlException;

}
