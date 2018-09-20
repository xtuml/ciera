package io.ciera.template.util;

import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.IXtumlType;
import io.ciera.summit.types.XtumlString;

public interface TEMP {
    
    public void append( XtumlString s ) throws XtumlException;

    default public void append( boolean b ) throws XtumlException {
        append( new XtumlString( Boolean.toString( b ) ) );
    }

    default public void append( int i ) throws XtumlException {
        append( new XtumlString( Integer.toString( i ) ) );
    }

    default public void append( double d ) throws XtumlException {
        append( new XtumlString( Double.toString( d ) ) );
    }

    default public <T extends IXtumlType<T>>void append( T o ) throws XtumlException {
        append( new XtumlString( o.toString() ) );
    }

    public XtumlString body() throws XtumlException;
    public void clear() throws XtumlException;
    public void emit( XtumlString file ) throws XtumlException;
    public void include( XtumlString file ) throws XtumlException;
    public XtumlString sub( XtumlString format, boolean b ) throws XtumlException;
    public XtumlString sub( XtumlString format, int i ) throws XtumlException;
    public XtumlString sub( XtumlString format, double d ) throws XtumlException;
    public XtumlString sub( XtumlString format, XtumlString s ) throws XtumlException;
    public XtumlString sub( XtumlString format, IXtumlType<?> o ) throws XtumlException;
    
    public void pushBuffer();
    public void popBuffer();

}
