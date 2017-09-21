package com.cierasystems.cairn.ees;

import com.cierasystems.summit.application.Application;

public class DefaultARCH {
    
    public static void shutdown() {
        Application.app.stop();
    }

}
