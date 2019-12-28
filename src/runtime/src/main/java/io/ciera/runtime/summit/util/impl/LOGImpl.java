package io.ciera.runtime.summit.util.impl;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.types.TimeStamp;
import io.ciera.runtime.summit.util.LOG;
import io.ciera.runtime.summit.util.Utility;

public class LOGImpl<C extends IComponent<C>> extends Utility<C> implements LOG {

    public LOGImpl(C context) {
        super(context);
    }

    @Override
    public void LogFailure(String message) {
        getRunContext().getLog().error(message);
    }

    @Override
    public void LogInfo(String message) {
        getRunContext().getLog().info(message);
    }

    @Override
    public void LogSuccess(String message) {
        getRunContext().getLog().info(message);
    }

    @Override
    public void LogInteger(int message) {
        getRunContext().getLog().info("%d", message);
    }

    @Override
    public void LogReal(String message, double r) {
        getRunContext().getLog().info("%s %f", message, r);
    }

    @Override
    public void LogTime(String message, TimeStamp t) {
        getRunContext().getLog().info("%s %s", message, t);
    }

}
