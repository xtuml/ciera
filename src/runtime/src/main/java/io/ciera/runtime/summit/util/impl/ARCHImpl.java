package io.ciera.runtime.summit.util.impl;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlExitException;
import io.ciera.runtime.summit.util.ARCH;
import io.ciera.runtime.summit.util.Utility;

public class ARCHImpl<C extends IComponent<C>> extends Utility<C> implements ARCH {

    public ARCHImpl(C context) {
        super(context);
    }

    @Override
    public void shutdown() {
    	context().getApp().stop();
    }

    @Override
    public void exit(int code) {
        throw new XtumlExitException(code);
    }

    @Override
    public void openURL(String url) {
      // no-op
    }

}
