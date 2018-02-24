package io.ciera.cairn.util;

import io.ciera.summit.statemachines.IEvent;
import io.ciera.summit.time.Date;
import io.ciera.summit.time.TimeStamp;
import io.ciera.summit.time.Timer;

public class DefaultTIM {
    
    public static Date create_date( int day, int hour, int minute, int month, int second, int year ) {
        return new Date( year, month, day, hour, minute, second );
    }
    
    public static TimeStamp current_clock() {
        return new TimeStamp();
    }
    
    public static Date current_date() {
        return new Date( new TimeStamp() );
    }
    
    public static int get_day( Date date ) {
        return date.getDay();
    }

    public static int get_hour( Date date ) {
        return date.getHour();
    }
    
    public static int get_minute( Date date ) {
        return date.getMinute();
    }
    
    public static int get_month( Date date ) {
        return date.getMonth();
    }
    
    public static int get_second( Date date ) {
        return date.getSecond();
    }
    
    public static int get_year( Date date ) {
        return date.getYear();
    }
    
    public static boolean timer_add_time( int microseconds, Timer timer_inst_ref ) {
        //Application.app.getTimeKeeper().addTime( timer_inst_ref, microseconds );
        return true;
    }
    
    public static boolean timer_cancel( Timer timer_inst_ref ) {
        //Application.app.getTimeKeeper().cancel( timer_inst_ref );
        return true;
    }
    
    public static int timer_remaining_time( Timer timer_inst_ref ) {
        //return Application.app.getTimeKeeper().getRemainingTime( timer_inst_ref );
    	return 0;
    }

    public static boolean timer_reset_time( int microseconds, Timer timer_inst_ref ) {
        //Application.app.getTimeKeeper().resetTime( timer_inst_ref, microseconds );
        return true;
    }

    public static Timer timer_start( IEvent event_inst, int microseconds) {
        //return Application.app.getTimeKeeper().newTimer( event_inst, microseconds, false );
    	return null;
    }

    public static Timer timer_start_recurring( IEvent event_inst, int microseconds) {
        //return Application.app.getTimeKeeper().newTimer( event_inst, microseconds, false );
    	return null;
    }

}
