package io.ciera.cairn.ees;

import io.ciera.summit.application.Application;

public class DefaultARCH {
    
    public static void shutdown() {
        Application.app.stop();
    }

}
