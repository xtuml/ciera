package io.ciera.cairn.types;

import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.IXtumlType;

public class XtumlString implements IXtumlType<XtumlString> {
    
    private String str;
    
    public XtumlString( String str ) {
        this.str = str;
    }

    @Override
    public boolean equality( XtumlString value ) throws XtumlException {
        return null != str && null != value && str.equals( value.toString() );
    }
    
    public XtumlString concat( XtumlString value ) {
        if ( str == null ) return new XtumlString( value.toString() );
        else return new XtumlString( str + value.toString() );
    }

    @Override
    public XtumlString defaultValue() {
        return new XtumlString( "" );
    }

    @Override
    public String toString() {
        return str;
    }

}
