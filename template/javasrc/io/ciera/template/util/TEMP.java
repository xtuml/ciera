package io.ciera.template.util;

import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.XtumlString;

public interface TEMP {
    
    public void append( XtumlString s ) throws XtumlException;
    public XtumlString body() throws XtumlException;
    public void clear() throws XtumlException;
    public void emit( XtumlString file ) throws XtumlException;
    public void include( XtumlString file ) throws XtumlException;
    public XtumlString sub( XtumlString format, XtumlString s ) throws XtumlException;
    
    public void pushBufferStack();
    public void popBufferStack();

}
