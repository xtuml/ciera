package io.ciera.runtime.instanceloading;

import java.io.OutputStream;
import java.util.List;

import io.ciera.runtime.summit.exceptions.NotImplementedException;
import io.ciera.runtime.summit.exceptions.XtumlException;

public interface IPopulationLoader {

    public void insert(String tableName, List<Object> values) throws XtumlException;

    public void finish() throws XtumlException;

    default public void serialize(OutputStream out) throws XtumlException {
    	throw new NotImplementedException("Not implemented");
    }
    
    default public void serialize(IChangeLog changeLog) throws XtumlException {
    	throw new NotImplementedException("Not implemented");
    }

}
