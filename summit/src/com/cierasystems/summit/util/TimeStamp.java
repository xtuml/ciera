package com.cierasystems.summit.util;

import com.cierasystems.summit.application.Application;

@SuppressWarnings("serial")
public class TimeStamp extends java.util.Date {

    public TimeStamp() {
        super( Application.app.getTimeKeeper().currentTimeMicro() / 1000 );
    }

}
