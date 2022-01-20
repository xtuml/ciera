package io.ciera.runtime.util;

import io.ciera.runtime.domain.Domain;
import io.ciera.runtime.domain.Utility;

public class LOG extends Utility {

    public LOG(Domain domain) {
        super(domain);
    }

    public void LogFailure(final String message) {
        getApplication().getLogger().error(message);
    }

    public void LogInfo(final String message) {
        getApplication().getLogger().info(message);
    }

    public void LogSuccess(final String message) {
        getApplication().getLogger().info(message);
    }

    public void LogInteger(final int message) {
        getApplication().getLogger().info("%d", message);
    }

    public void LogReal(final String message, final double r) {
        getApplication().getLogger().info("%s %f", message, r);
    }

    public void LogTime(final String message, final long t) {
        getApplication().getLogger().info("%s %d", message, t);
    }

}
