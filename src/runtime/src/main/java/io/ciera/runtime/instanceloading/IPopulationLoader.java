package io.ciera.runtime.instanceloading;

import io.ciera.runtime.summit.exceptions.XtumlException;

public interface IPopulationLoader {

	public void load() throws XtumlException;

    public void serialize() throws XtumlException;
    
    public void serialize(IChangeLog changeLog) throws XtumlException;

}
