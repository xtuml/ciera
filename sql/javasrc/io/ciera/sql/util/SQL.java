package io.ciera.sql.util;

import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.XtumlString;

public interface SQL {
    
    public void load() throws XtumlException;
    public void load_file( XtumlString file ) throws XtumlException;
    public void serialize() throws XtumlException;
    public void serialize_file( XtumlString file ) throws XtumlException;

}
