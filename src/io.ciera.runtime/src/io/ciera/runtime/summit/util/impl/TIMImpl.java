package io.ciera.runtime.summit.util.impl;

import java.util.Calendar;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.NotImplementedException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.IEvent;
import io.ciera.runtime.summit.time.Timer;
import io.ciera.runtime.summit.types.Date;
import io.ciera.runtime.summit.types.TimeStamp;
import io.ciera.runtime.summit.util.TIM;
import io.ciera.runtime.summit.util.Utility;

public class TIMImpl<C extends IComponent<C>> extends Utility<C> implements TIM {

    public TIMImpl(C context) {
        super(context);
    }

    @Override
    public TimeStamp current_clock() throws XtumlException {
        return TimeStamp.now();
    }

    @Override
    public Date current_date() throws XtumlException {
        return Date.now();
    }

    @Override
    public int get_day(Date date) throws XtumlException {
        return date.getDay();
    }

    @Override
    public int get_month(Date date) throws XtumlException {
        return date.getMonth();
    }

    @Override
    public int get_year(Date date) throws XtumlException {
        return date.getYear();
    }

    @Override
    public int get_hour(Date date) throws XtumlException {
        return date.getHour();
    }

    @Override
    public int get_minute(Date date) throws XtumlException {
        return date.getMinute();
    }

    @Override
    public int get_second(Date date) throws XtumlException {
        return date.getSecond();
    }

    @Override
    public Date create_date(int day, int hour, int minute, int month, int second, int year) throws XtumlException {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, hour, minute, second);
        return new Date(cal.getTimeInMillis());
    }

    @Override
    public boolean timer_add_time(int microseconds, Timer timer_inst_ref) throws XtumlException {
        throw new NotImplementedException("Not implemented");
    }

    @Override
    public boolean timer_cancel(Timer timer_inst_ref) throws XtumlException {
        throw new NotImplementedException("Not implemented");
    }

    @Override
    public int timer_remaining_time(Timer timer_inst_ref) throws XtumlException {
        throw new NotImplementedException("Not implemented");
    }

    @Override
    public boolean timer_reset_time(int microseconds, Timer timer_inst_ref) throws XtumlException {
        throw new NotImplementedException("Not implemented");
    }

    @Override
    public Timer timer_start(IEvent event_inst, int microseconds) throws XtumlException {
        Timer timer = new Timer(event_inst, microseconds, false);
        getRunContext().addTimer(timer);
        return timer;
    }

    @Override
    public Timer timer_start_recurring(IEvent event_inst, int microseconds) throws XtumlException {
        Timer timer = new Timer(event_inst, microseconds, true);
        getRunContext().addTimer(timer);
        return timer;
    }

}
