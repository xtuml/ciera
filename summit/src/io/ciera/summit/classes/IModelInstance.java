package io.ciera.summit.classes;

import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.statemachine.EventTarget;
import io.ciera.summit.util.UniqueId;

public interface IModelInstance extends EventTarget, Comparable<IModelInstance> {
    
    public String getKeyLetters();
    public UniqueId getInstanceId();
    public void checkLiving() throws XtumlException;
    public IInstancePopulation getContext();
    public void setContext( IInstancePopulation context );
    public void delete() throws XtumlException;
    public IInstanceSet toSet();
    
}