package io.ciera.runtime.util;

import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.domain.Domain;

public class LOG {

    private final Application app;

    public LOG(Domain domain) {
        app = domain.getApplication();
    }

    public void LogFailure(final String message) {
        app.getLogger().error(message);
    }

    public void LogInfo(final String message) {
        app.getLogger().info(message);
    }

    public void LogSuccess(final String message) {
        app.getLogger().info(message);
    }

    public void LogInteger(final int message) {
        app.getLogger().info("%d", message);
    }

    public void LogReal(final String message, final double r) {
        app.getLogger().info("%s %f", message, r);
    }

    public void LogTime(final String message, final long t) {
        app.getLogger().info("%s %d", message, t);
    }

}
