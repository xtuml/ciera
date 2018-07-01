package io.ciera.cairn.util.impl;

import io.ciera.cairn.util.TIM;
import io.ciera.cairn.util.Utility;
import io.ciera.summit.components.IComponent;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.TimeStamp;

public class TIMImpl<C extends IComponent<C>> extends Utility<C> implements TIM {

    public TIMImpl( C context ) {
        super( context );
    }

    @Override
    public TimeStamp current_clock() throws XtumlException {
        return TimeStamp.now();
    }

}
