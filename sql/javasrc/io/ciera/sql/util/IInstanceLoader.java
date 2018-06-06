package io.ciera.sql.util;

import java.io.OutputStream;
import java.util.List;

import io.ciera.summit.exceptions.XtumlException;

public interface IInstanceLoader {
    
    public void insert( String tableName, List<Object> values );
    public void finish();
    
    public void serialize( OutputStream out ) throws XtumlException;

}
