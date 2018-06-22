package io.ciera.cairn.util.impl;

import io.ciera.cairn.util.LOG;
import io.ciera.cairn.util.Utility;
import io.ciera.summit.components.IComponent;
import io.ciera.summit.types.XtumlString;

public class LOGImpl<C extends IComponent<C>> extends Utility<C> implements LOG {

    public LOGImpl( C context ) {
        super( context );
    }

    @Override
    public void LogFailure( XtumlString message ) {
        System.err.printf( "ERROR: %s\n", message );
    }

    @Override
    public void LogInfo( XtumlString message ) {
        System.out.printf( "INFO: %s\n", message );
    }

    @Override
    public void LogSuccess( XtumlString message ) {
        System.out.printf( "SUCCESS: %s\n", message );
    }

    @Override
    public void LogInteger( int message ) {
        System.out.printf( "INTEGER: %d\n", message );
    }
    
    @Override
    public void LogReal( XtumlString message, double r ) {
        System.out.printf( "REAL: %s %f\n", message, r );
    }

}
