package io.ciera.runtime.summit.types;

import java.util.Calendar;

public class Date extends TimeStamp {

    private Calendar cal;

    public Date() {
        super();
        cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
    }

    public Date(long timestamp) {
        super(timestamp);
        cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
    }
    
    public int getYear() {
        return cal.get(Calendar.YEAR);
    }

    public int getMonth() {
        return cal.get(Calendar.MONTH) + 1;
    }

    public int getDay() {
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public int getHour() {
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return cal.get(Calendar.MINUTE);
    }

    public int getSecond() {
        return cal.get(Calendar.SECOND);
    }

    public static Date now() {
        return new Date(System.currentTimeMillis());
    }

}
