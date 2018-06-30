package io.ciera.summit.types;

import io.ciera.summit.exceptions.XtumlException;

public class XtumlString implements IXtumlType<XtumlString>, CharSequence {
    
    private String str;
    
    public XtumlString( String str ) {
        this.str = str;
    }
    
    @Override
    public boolean equals( Object o ) {
        return null != o && o instanceof XtumlString && null != str && str.equals( o.toString() );
    }
    
    @Override
    public int hashCode() {
        return null == str ? 0 : str.hashCode();
    }

    @Override
    public boolean equality( XtumlString value ) throws XtumlException {
        return equals( value );
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

    @Override
    public int length() {
        if ( null != str ) return str.length();
        else return 0;
    }

    @Override
    public char charAt( int index ) {
        if ( null != str ) return str.charAt( index );
        else return 0;
    }

    @Override
    public CharSequence subSequence( int start, int end ) {
        if ( null != str ) return new XtumlString( str.substring( start, end ) );
        else return null;
    }

}
