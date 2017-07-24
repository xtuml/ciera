package ciera.util;

import java.util.TimerTask;

import ciera.classes.exceptions.EmptyInstanceException;
import ciera.statemachine.Event;

public class Timer {
    
    private java.util.Timer internalTimer;

    private Event eventToGenerate;
    private int period;
    private java.util.Date target;
    private boolean recurring;
    
    public Timer( Event e, int microseconds, boolean recur ) {
        eventToGenerate = e;
        recurring = recur;
        period = microseconds;
        target = new java.util.Date( new TimeStamp().getTime() + ( period / 1000 ) );
        internalTimer = new java.util.Timer();
        internalTimer.schedule( new GenerateTask(), target );
    }
    
    public void addTime( int microseconds ) {
        internalTimer.cancel();
        target = new java.util.Date( target.getTime() + ( microseconds / 1000 ) );
        internalTimer.schedule( new GenerateTask(), target );
    }
    
    public int getRemainingTime() {
        return (int)( target.getTime() - new TimeStamp().getTime() );
    }
    
    public void resetTime( int microseconds ) {
        internalTimer.cancel();
        period = microseconds;
        target = new java.util.Date( new TimeStamp().getTime() + ( period / 1000 ) );
        internalTimer.schedule( new GenerateTask(), target );
    }

    public void cancel() {
        internalTimer.cancel();
    }
    
    private class GenerateTask extends TimerTask {
        @Override
        public void run() {
            // generate the event
            try {
                eventToGenerate.generate();
            } catch (EmptyInstanceException e) {
                // TODO exception handling
            }
            // if this is a recurring timer, reschedule
            if ( recurring ) {
                target = new java.util.Date( new TimeStamp().getTime() + ( period / 1000 ) );
                internalTimer.schedule( new GenerateTask(), target );
            }
        }
    }

}
