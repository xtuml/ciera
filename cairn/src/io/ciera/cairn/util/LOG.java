package io.ciera.cairn.util;

import io.ciera.cairn.types.XtumlString;

public interface LOG {

    public void LogFailure( XtumlString message );
    public void LogInfo( XtumlString message );
    public void LogSuccess( XtumlString message );
    public void LogInteger( int message );

}
