package ciera.util.ees;

import ciera.application.XtumlApplication;

public class DefaultARCH {
    
    public static void shutdown() {
        XtumlApplication.app.stop();
    }

}
