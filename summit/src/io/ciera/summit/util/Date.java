package io.ciera.summit.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date {
    
    private GregorianCalendar internalCalendar;
    
    public Date( int year, int month, int day, int hour, int minute, int second ) {
        internalCalendar = new GregorianCalendar();
        internalCalendar.set( year, month, day, hour, minute, second );
    }
    
    public Date( TimeStamp timestamp ) {
        internalCalendar = new GregorianCalendar();
        internalCalendar.setTimeInMillis( timestamp.getTime() );
    }
    
    public int getYear() {
        return internalCalendar.get( Calendar.YEAR );
    }
    
    public int getMonth() {
        return internalCalendar.get( Calendar.MONTH );
    }
    
    public int getDay() {
        return internalCalendar.get( Calendar.DAY_OF_MONTH );
    }
    
    public int getHour() {
        return internalCalendar.get( Calendar.HOUR );
    }
    
    public int getMinute() {
        return internalCalendar.get( Calendar.MINUTE );
    }
    
    public int getSecond() {
        return internalCalendar.get( Calendar.SECOND );
    }

}
