package io.ciera.runtime.summit.util.impl;

import io.ciera.runtime.summit.util.LOG;
import io.ciera.runtime.summit.util.Utility;
import io.ciera.summit.components.IComponent;
import io.ciera.summit.types.TimeStamp;

public class LOGImpl<C extends IComponent<C>> extends Utility<C> implements LOG {

    public LOGImpl( C context ) {
        super( context );
    }

    @Override
    public void LogFailure( String message ) {
        System.err.printf( "ERROR: %s\n", message );
    }

    @Override
    public void LogInfo( String message ) {
        System.out.printf( "INFO: %s\n", message );
    }

    @Override
    public void LogSuccess( String message ) {
        System.out.printf( "SUCCESS: %s\n", message );
    }

    @Override
    public void LogInteger( int message ) {
        System.out.printf( "INTEGER: %d\n", message );
    }
    
    @Override
    public void LogReal( String message, double r ) {
        System.out.printf( "REAL: %s %f\n", message, r );
    }

    @Override
    public void LogTime( String message, TimeStamp t ) {
        System.out.printf( "TIME: %s %s\n", message, t );
    }

}
