package com.cierasystems.summit.classes;

import com.cierasystems.summit.exceptions.XtumlException;
import com.cierasystems.summit.statemachine.EventTarget;
import com.cierasystems.summit.util.UniqueId;

public interface IModelInstance extends EventTarget, Comparable<IModelInstance> {
    
    public int getClassNumber();
    public String getKeyLetters();
    public UniqueId getInstanceId();
    public void checkLiving() throws XtumlException;
    public IInstancePopulation getContext();
    public void setContext( IInstancePopulation context );
    public void delete() throws XtumlException;
    public IInstanceSet toSet();
    
}