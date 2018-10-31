package io.ciera.runtime.summit.util;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.TimeStamp;

public interface TIM {

    public TimeStamp current_clock() throws XtumlException;

}
