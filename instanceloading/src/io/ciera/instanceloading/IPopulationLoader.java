package io.ciera.instanceloading;

import java.io.OutputStream;
import java.util.List;

import io.ciera.summit.exceptions.XtumlException;

public interface IPopulationLoader {
    
    public void insert( String tableName, List<Object> values ) throws XtumlException;
    public void finish() throws XtumlException;
    public void serialize( OutputStream out ) throws XtumlException;

}
