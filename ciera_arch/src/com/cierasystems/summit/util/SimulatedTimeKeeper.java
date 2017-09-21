package ciera.util;

import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import ciera.application.XtumlApplication;
import ciera.application.XtumlTask;
import ciera.exceptions.XtumlException;
import ciera.statemachine.Event;

public class SimulatedTimeKeeper implements TimeKeeper {
    
    // singleton instance
    private static SimulatedTimeKeeper timeKeeper = new SimulatedTimeKeeper();
    
    private AtomicLong currentTime;
    private AtomicBoolean taskInQueue;

    private SortedSet<Timer> runningTimers;
    
    // constructor
    private SimulatedTimeKeeper() {
        currentTime = new AtomicLong( 0 );
        runningTimers = new ConcurrentSkipListSet<Timer>();
        taskInQueue = new AtomicBoolean( false );
    }
    
    public static SimulatedTimeKeeper getInstance() {
        return timeKeeper;
    }

    // public methods
    public synchronized void addTime( Timer t, int microseconds ) {
        if ( null != t && runningTimers.remove( t ) ) {
            t.addToWakeUpTime( microseconds );
            addTimer( t );
        }
    }

    public int getRemainingTime( Timer t ) {
        return (int)( t.getWakeUpTime() - currentTimeMicro() );
    }

    public synchronized void resetTime( Timer t, int microseconds ) {
        if ( null != t && runningTimers.remove( t ) ) {
            t.setPeriod( microseconds );
            t.calculateWakeUpTime();
            addTimer( t );
        }
    }

    public synchronized void cancel( Timer t ) {
        if ( null != t ) runningTimers.remove( t );
    }
    
    public synchronized Timer newTimer( Event e, int microseconds, boolean recurring ) {
        Timer timer = null;
        if ( null != e ) {
            timer = new Timer( e, microseconds, recurring );
            addTimer( timer );
        }
        return timer;
    }
    
    private void addTimer( Timer timer ) {
        if ( timer != null ) runningTimers.add( timer );
        if ( !taskInQueue.get() ) {
            XtumlApplication.app.getExecutor().execute( new PopTimerTask() );
            taskInQueue.set( true );
        }
    }
    
    private class PopTimerTask extends XtumlTask {
        @Override
        public void init() throws XtumlException {
            synchronized ( SimulatedTimeKeeper.this ) {
                // get the timer
                Timer timer = runningTimers.first();
                if ( null != timer && runningTimers.remove( timer ) ) {
                    // set the clock
                    currentTime.set( timer.getWakeUpTime() );
                    // generate the event
                    timer.getEventToGenerate().generate();
                    // if this is a recurring timer, reschedule
                    if ( timer.isRecurring() ) {
                        timer.calculateWakeUpTime();
                        addTimer( timer );
                    }
                    // add a new timer task
                    XtumlApplication.app.getExecutor().execute( new PopTimerTask() );
                    taskInQueue.set( true );
                }
                else {
                    taskInQueue.set( false );
                }
            }
        }
    }
    
    // utility methods
    public long currentTimeMicro() {
        return currentTime.get();
    }
    
    static long microToMillis( long microseconds ) {
        long millis = microseconds / 1000;
        if ( microseconds % 1000 != 0 ) millis++;  // if there are any remaining microseconds, round up
        return millis;
    }
    
}
