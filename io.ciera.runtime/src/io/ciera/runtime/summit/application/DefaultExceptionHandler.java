package io.ciera.runtime.summit.application;

import io.ciera.runtime.summit.util.PerformanceUtil;
import io.ciera.summit.application.IExceptionHandler;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.exceptions.XtumlInterruptedException;

public class DefaultExceptionHandler implements IExceptionHandler {

    @Override
    public void handle( XtumlException e ) {
        if ( e instanceof XtumlInterruptedException ) {
            // Ignore interrupted exceptions
        }
        else {
            e.printStackTrace( System.err );
            PerformanceUtil.generateReport();
            System.exit( 1 );
        }
    }

    @Override
    public void warn( String message ) {
        System.err.println( message );
    }

}
