package ciera.util.ees;

import ciera.application.XtumlApplication;

public class ARCH {
    
    public static void shutdown() {
        XtumlApplication.app.stop();
    }

}
