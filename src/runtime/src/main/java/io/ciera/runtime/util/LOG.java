package io.ciera.runtime.util;

import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.domain.Domain;
import io.ciera.runtime.domain.Utility;

public class LOG extends Utility {

    public LOG(Domain domain) {
        super(domain);
    }

    public LOG(Domain domain, ExecutionContext context) {
        super(domain, context);
    }

    public void LogFailure(final String message) {
        getLogger().error(message);
    }

    public void LogInfo(final String message) {
        getLogger().info(message);
    }

    public void LogSuccess(final String message) {
        getLogger().info(message);
    }

    public void LogInteger(final int message) {
        getLogger().info("%d", message);
    }

    public void LogReal(final String message, final double r) {
        getLogger().info("%s %f", message, r);
    }

    public void LogTime(final String message, final long t) {
        getLogger().info("%s %d", message, t);
    }

}
