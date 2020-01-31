package io.ciera.runtime.summit.util.impl;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.util.SORT;
import io.ciera.runtime.summit.util.Utility;

public class SORTImpl<C extends IComponent<C>> extends Utility<C> implements SORT {

    public SORTImpl(C context) {
        super(context);
    }

    @Override
    public boolean ascending(final String attr) {
        return true;
    }

    @Override
    public boolean descending(final String attr) {
        return true;
    }

}
