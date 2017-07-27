package ciera.util.ees;

import ciera.statemachine.Event;
import ciera.util.Date;
import ciera.util.TimeKeeper;
import ciera.util.TimeStamp;
import ciera.util.Timer;

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
        TimeKeeper.getInstance().addTime( timer_inst_ref, microseconds );
        return true;
    }
    
    public static boolean timer_cancel( Timer timer_inst_ref ) {
        TimeKeeper.getInstance().cancel( timer_inst_ref );
        return true;
    }
    
    public static int timer_remaining_time( Timer timer_inst_ref ) {
        return TimeKeeper.getInstance().getRemainingTime( timer_inst_ref );
    }

    public static boolean timer_reset_time( int microseconds, Timer timer_inst_ref ) {
        TimeKeeper.getInstance().resetTime( timer_inst_ref, microseconds );
        return true;
    }

    public static Timer timer_start( Event event_inst, int microseconds) {
        return TimeKeeper.getInstance().newTimer( event_inst, microseconds, false );
    }

    public static Timer timer_start_recurring( Event event_inst, int microseconds) {
        return TimeKeeper.getInstance().newTimer( event_inst, microseconds, false );
    }

}
