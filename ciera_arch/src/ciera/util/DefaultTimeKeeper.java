package ciera.util;

import java.util.SortedSet;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentSkipListSet;

import ciera.exceptions.XtumlException;
import ciera.statemachine.Event;

public class DefaultTimeKeeper implements TimeKeeper {
    
    // singleton instance
    private static DefaultTimeKeeper timeKeeper = new DefaultTimeKeeper();

    private SortedSet<Timer> runningTimers;
    private java.util.Timer internalTimer;
    
    // constructor
    private DefaultTimeKeeper() {
        runningTimers = new ConcurrentSkipListSet<Timer>();
        internalTimer = new java.util.Timer();
    }
    
    public static DefaultTimeKeeper getInstance() {
        return timeKeeper;
    }

    // public methods
    public synchronized void addTime( Timer t, int microseconds ) {
        if ( null != t && runningTimers.remove( t ) ) {
            t.addToWakeUpTime( microseconds );
            addTimer( t );
            reschedule();
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
            reschedule();
        }
    }

    public synchronized void cancel( Timer t ) {
        if ( null != t && runningTimers.remove( t ) ) {
            reschedule();
        }
    }
    
    public synchronized Timer newTimer( Event e, int microseconds, boolean recurring ) {
        Timer timer = null;
        if ( null != e ) {
            timer = new Timer( e, microseconds, recurring );
            addTimer( timer );
            reschedule();
        }
        return timer;
    }
    
    private void addTimer( Timer timer ) {
        if ( timer != null ) runningTimers.add( timer );
    }
    
    private void reschedule() {
        internalTimer.cancel();
        if ( !runningTimers.isEmpty() ) {
            internalTimer = new java.util.Timer();
            internalTimer.schedule( new GenerateTask(), new java.util.Date( microToMillis( runningTimers.first().getWakeUpTime() ) ) );
        }
    }
    
    private class GenerateTask extends TimerTask {
        @Override
        public void run() {
            synchronized ( DefaultTimeKeeper.this ) {
              // get the timer
              Timer timer = runningTimers.first();
              if ( null != timer && runningTimers.remove( timer ) ) {
                  // generate the event
                  try {
                      timer.getEventToGenerate().generate();
                  } catch ( XtumlException e ) {
                      // TODO exception handling
                      System.err.println( "Bad 4" );
                      e.printStackTrace();
                  }
                  // if this is a recurring timer, reschedule
                  if ( timer.isRecurring() ) {
                      timer.calculateWakeUpTime();
                      addTimer( timer );
                  }
                  // reschedule for the next timer
                  reschedule();
              }
            }
        }
    }
    
    // utility methods
    public static long currentTimeMicro() {
        return System.currentTimeMillis() * 1000;  // time in microseconds since the epoch
    }
    
    public static long microToMillis( long microseconds ) {
        long millis = microseconds / 1000;
        if ( microseconds % 1000 != 0 ) millis++;  // if there are any remaining microseconds, round up
        return millis;
    }
    
}
