package io.ciera.cairn.util.impl;

import io.ciera.cairn.util.LOG;
import io.ciera.cairn.util.Utility;
import io.ciera.summit.components.IComponent;

public class LOGImpl<C extends IComponent<C>> extends Utility<C> implements LOG {

    public LOGImpl( C population ) {
        super( population );
    }

    @Override
    public void LogFailure(String message) {
        System.err.printf( "ERROR: %s\n", message );
    }

    @Override
    public void LogInfo(String message) {
        System.out.printf( "INFO: %s\n", message );
    }

    @Override
    public void LogSuccess(String message) {
        System.out.printf( "SUCCESS: %s\n", message );
    }

}
