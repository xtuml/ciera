package io.ciera.runtime.summit.util.impl;

import java.util.Calendar;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.EventHandle;
import io.ciera.runtime.summit.time.Timer;
import io.ciera.runtime.summit.time.TimerHandle;
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
        return TimeStamp.now(getRunContext());
    }

    @Override
    public Date current_date() throws XtumlException {
        return Date.now(getRunContext());
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
    public boolean timer_add_time(int microseconds, TimerHandle timer_inst_ref) throws XtumlException {
    	Timer timer = getRunContext().getTimer(timer_inst_ref);
    	if ( null != timer ) {
    		timer.addTime(microseconds);
    		return true;
    	}
    	else return false;
    }

    @Override
    public boolean timer_cancel(TimerHandle timer_inst_ref) throws XtumlException {
        return getRunContext().cancelTimer(timer_inst_ref);
    }

    @Override
    public int timer_remaining_time(TimerHandle timer_inst_ref) throws XtumlException {
    	Timer timer = getRunContext().getTimer(timer_inst_ref);
    	if ( null != timer ) {
            return timer.getWakeUpTime() - Math.toIntExact(getRunContext().time());
    	}
    	else return 0;
    }

    @Override
    public boolean timer_reset_time(int microseconds, TimerHandle timer_inst_ref) throws XtumlException {
    	Timer timer = getRunContext().getTimer(timer_inst_ref);
    	if ( null != timer ) {
            timer.setPeriod(microseconds);
            timer.reset(Math.toIntExact(getRunContext().time()));
    		return true;
    	}
    	else return false;
    }

    @Override
    public TimerHandle timer_start(EventHandle event_inst, int microseconds) throws XtumlException {
        Timer timer = new Timer(event_inst, microseconds, false);
        timer.reset(Math.toIntExact(getRunContext().time()));
        return getRunContext().addTimer(timer);
    }

    @Override
    public TimerHandle timer_start_recurring(EventHandle event_inst, int microseconds) throws XtumlException {
        Timer timer = new Timer(event_inst, microseconds, true);
        timer.reset(Math.toIntExact(getRunContext().time()));
        return getRunContext().addTimer(timer);
    }

}
