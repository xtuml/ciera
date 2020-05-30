package io.ciera.runtime.summit.util.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.util.LOG;
import io.ciera.runtime.summit.util.Utility;

public class LOGImpl<C extends IComponent<C>> extends Utility<C> implements LOG {

    public LOGImpl(C context) {
        super(context);
    }

    @Override
    public void LogFailure(final String message) {
        getRunContext().getLog().error(message);
    }

    @Override
    public void LogInfo(final String message) {
        getRunContext().getLog().info(message);
    }

    @Override
    public void LogSuccess(final String message) {
        getRunContext().getLog().info(message);
    }

    @Override
    public void LogInteger(final int message) {
        getRunContext().getLog().info("%d", message);
    }

    @Override
    public void LogReal(final String message, final double r) {
        getRunContext().getLog().info("%s %f", message, r);
    }

    @Override
    public void LogTime(final String message, final long t) {
        IRunContext executor = getRunContext();
        long unixNanos = (executor.time() - executor.getEpoch().until(Instant.EPOCH, ChronoUnit.MICROS)) * 1000L;
        String stamp = Instant.ofEpochSecond(unixNanos / 1000000000L, unixNanos % 1000000000L).toString();
        executor.getLog().info("%s %s", message, stamp);
    }

}
