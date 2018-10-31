package io.ciera.runtime.summit.util.impl;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.TimeStamp;
import io.ciera.runtime.summit.util.TIM;
import io.ciera.runtime.summit.util.Utility;

public class TIMImpl<C extends IComponent<C>> extends Utility<C> implements TIM {

    public TIMImpl( C context ) {
        super( context );
    }

    @Override
    public TimeStamp current_clock() throws XtumlException {
        return TimeStamp.now();
    }

}
