package io.ciera.runtime.summit.util;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.Date;
import io.ciera.runtime.summit.types.TimeStamp;

public interface TIM {

    public TimeStamp current_clock() throws XtumlException;
    public Date current_date() throws XtumlException;
    public int get_day( Date date ) throws XtumlException;
    public int get_month( Date date ) throws XtumlException;
    public int get_year( Date date ) throws XtumlException;
    public int get_hour( Date date ) throws XtumlException;
    public int get_minute( Date date ) throws XtumlException;
    public int get_second( Date date ) throws XtumlException;

}
