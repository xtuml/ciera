package io.ciera.runtime.instanceloading.sql.util;

import io.ciera.summit.exceptions.XtumlException;

public interface SQL {
    
    public void load() throws XtumlException;
    public void load_file( String file ) throws XtumlException;
    public void serialize() throws XtumlException;
    public void serialize_file( String file ) throws XtumlException;

}
