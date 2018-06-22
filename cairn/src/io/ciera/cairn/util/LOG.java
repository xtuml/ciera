package io.ciera.cairn.util;

import io.ciera.summit.types.XtumlString;

public interface LOG {

    public void LogFailure( XtumlString message );
    public void LogInfo( XtumlString message );
    public void LogSuccess( XtumlString message );
    public void LogInteger( int message );
    public void LogReal( XtumlString message, double r );

}
