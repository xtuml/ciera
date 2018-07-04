package io.ciera.summit.types;

import io.ciera.summit.exceptions.XtumlException;

public class TimeStamp implements IXtumlType<TimeStamp>, Comparable<TimeStamp> {

    private long timestamp;

    public TimeStamp() {
        timestamp = System.currentTimeMillis();
    }
    
    public TimeStamp( long timestamp ) {
        this.timestamp = timestamp;
    }
    
    public TimeStamp subtract( TimeStamp t ) throws XtumlException {
        if ( null == t ) return null;
        else if ( timestamp < t.timestamp ) throw new XtumlException( "Negative timestamp" );
        else return new TimeStamp( timestamp - t.timestamp );
    }
    
    @Override
    public int compareTo( TimeStamp o ) {
        return timestamp == o.timestamp ? 0 : timestamp < o.timestamp ? -1 : 1;
    }

    @Override
    public boolean equality( TimeStamp value ) throws XtumlException {
        return equals( value );
    }

    @Override
    public TimeStamp defaultValue() {
        return new TimeStamp( 0 );
    }

    @Override
    public boolean equals( Object o ) {
        return o instanceof TimeStamp && timestamp == ((TimeStamp)o).timestamp;
    }

    @Override
    public int hashCode() {
        return Long.hashCode( timestamp );
    }
    
    @Override
    public String toString() {
        return Long.toString( timestamp );
    }

}
