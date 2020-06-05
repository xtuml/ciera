package io.ciera.runtime.summit.util.impl;

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
        getRunContext().getLog().info("%s %d", message, t);
    }

}
