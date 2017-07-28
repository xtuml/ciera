package ciera.util;

import ciera.application.XtumlApplication;

@SuppressWarnings("serial")
public class TimeStamp extends java.util.Date {

    public TimeStamp() {
        super( XtumlApplication.app.getTimeKeeper().currentTimeMicro() / 1000 );
    }

}
