package io.ciera.cairn.util;

import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.TimeStamp;

public interface TIM {

    public TimeStamp current_clock() throws XtumlException;

}
