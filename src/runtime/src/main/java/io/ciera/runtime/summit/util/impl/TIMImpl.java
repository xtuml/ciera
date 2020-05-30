package io.ciera.runtime.summit.util.impl;

import java.util.Calendar;

import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.EventHandle;
import io.ciera.runtime.summit.time.Timer;
import io.ciera.runtime.summit.time.TimerHandle;
import io.ciera.runtime.summit.types.Date;
import io.ciera.runtime.summit.util.TIM;
import io.ciera.runtime.summit.util.Utility;

public class TIMImpl<C extends IComponent<C>> extends Utility<C> implements TIM {

    public TIMImpl(C context) {
        super(context);
    }

    @Override
    public long advance_time(final int microseconds) throws XtumlException {
        IRunContext executor = getRunContext();
        executor.setTime(executor.time() + microseconds);
        return executor.time();
    }

    @Override
    public Date create_date(final int day, final int hour, final int minute, final int month, final int second, final int year) throws XtumlException {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, hour, minute, second);
        return new Date(cal.getTimeInMillis());
    }

    @Override
    public long current_clock() throws XtumlException {
        return getRunContext().time();
    }

    @Override
    public Date current_date() throws XtumlException {
        return Date.now(getRunContext());
    }

    @Override
    public int current_seconds() throws XtumlException {
        return (int)(current_clock() / 1000000L);
    }

    @Override
    public int get_day(final Date date) {
        return date.getDay();
    }

    @Override
    public int get_month(final Date date) {
        return date.getMonth();
    }

    @Override
    public int get_year(final Date date) {
        return date.getYear();
    }

    @Override
    public int get_hour(final Date date) {
        return date.getHour();
    }

    @Override
    public int get_minute(final Date date) {
        return date.getMinute();
    }

    @Override
    public int get_second(final Date date) {
        return date.getSecond();
    }

    @Override
    public void set_epoch(final int day, final int month, final int year) {
        // TODO panda
    }

    @Override
    public long set_time(final int year, final int month, final int day, final int hour, final int minute, final int second, final int microsecond) {
        return 0L;  // TODO panda
    }

    @Override
    public long time_of_day(final long timeval) {
        return 0L;  // TODO panda
    }

    @Override
    public boolean timer_add_time(final int microseconds, final TimerHandle timer_inst_ref) {
    	Timer timer = getRunContext().getTimer(timer_inst_ref);
    	if ( null != timer ) {
    		long oldTime = timer.getWakeUpTime();
    		timer.addTime(microseconds);
    		getRunContext().addChange(new Timer.TimerAttributeChangedDelta(timer, "wakeUpTime", oldTime, timer.getWakeUpTime()));
    		return true;
    	}
    	else return false;
    }

    @Override
    public boolean timer_cancel(final TimerHandle timer_inst_ref) {
        return getRunContext().cancelTimer(timer_inst_ref);
    }

    @Override
    public int timer_remaining_time(final TimerHandle timer_inst_ref) {
    	Timer timer = getRunContext().getTimer(timer_inst_ref);
    	if ( null != timer ) {
            return Math.toIntExact(timer.getWakeUpTime() - getRunContext().time());
    	}
    	else return 0;
    }

    @Override
    public boolean timer_reset_time(final int microseconds, final TimerHandle timer_inst_ref) {
    	Timer timer = getRunContext().getTimer(timer_inst_ref);
    	if ( null != timer ) {
    		int oldPeriod = timer.getPeriod();
    		long oldTime = timer.getWakeUpTime();
            timer.setPeriod(microseconds);
            timer.reset(getRunContext().time());
    		getRunContext().addChange(new Timer.TimerAttributeChangedDelta(timer, "period", oldPeriod, timer.getPeriod()));
    		getRunContext().addChange(new Timer.TimerAttributeChangedDelta(timer, "wakeUpTime", oldTime, timer.getWakeUpTime()));
    		return true;
    	}
    	else return false;
    }

    @Override
    public TimerHandle timer_start(final EventHandle event_inst, final int microseconds) {
        Timer timer = new Timer(context().getId(), event_inst, microseconds, false);
        timer.reset(getRunContext().time());
        return getRunContext().addTimer(timer);
    }

    @Override
    public TimerHandle timer_start_recurring(final EventHandle event_inst, final int microseconds) {
        Timer timer = new Timer(context().getId(), event_inst, microseconds, true);
        timer.reset(getRunContext().time());
        return getRunContext().addTimer(timer);
    }

    public String timestamp_format(final long ts, final String format) {
        return "";  // TODO panda
    }

    public String timestamp_to_string(final long timestamp) {
        return ""; // TODO panda
    }

}
