package io.ciera.cairn.util;

import io.ciera.summit.application.Application;

public final class DefaultARCH {

    public static void shutdown() {
        Application.app.stop();
    }

}
