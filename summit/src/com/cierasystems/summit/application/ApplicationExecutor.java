package com.cierasystems.summit.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ApplicationExecutor implements Executor {

    private BlockingQueue<ApplicationTask> tasks;
    private Map<Thread, ApplicationTask> taskMap;
    private boolean setNumberOfThreads;
    private boolean running;
    
    private List<XtumlApplicationThread> threads;
    
    public ApplicationExecutor() {
        setNumberOfThreads = false;
        running = false;
        tasks = new LinkedBlockingQueue<ApplicationTask>();
        taskMap = new ConcurrentHashMap<Thread, ApplicationTask>();
        threads = new ArrayList<XtumlApplicationThread>( 1 );
        threads.add( new XtumlApplicationThread( "Application thread 1") );
    }

    @Override
    public void execute( Runnable command ) {
        if ( null != command && command instanceof ApplicationTask ) {
            tasks.add( (ApplicationTask)command );
        }
    }

    public void start() {
        running = true;
        for ( XtumlApplicationThread thread : threads ) {
            thread.start();
        }
    }
    
    public void stop() {
        for ( XtumlApplicationThread thread : threads ) {
            thread.kill();
        }
    }
    
    public synchronized void setNumberThreads( int size ) {
        if ( !setNumberOfThreads && !running ) {
            setNumberOfThreads = true;
            threads = new ArrayList<XtumlApplicationThread>( size );
            for ( int i = 0; i < size; i++ ) threads.add( new XtumlApplicationThread( String.format( "Application thread %d", i+1 ) ) );
        }
    }
    
    public void addInitialTask( ApplicationTask task ) {
        if ( !running ) tasks.add( task );
    }

    public ApplicationTask getCurrentTask() {
        return taskMap.get( Thread.currentThread() );
    }
    
    private void beforeExecute( Thread t, ApplicationTask task ) {
        taskMap.put( t, task );
    }
    
    private void afterExecute( Thread t ) {
        taskMap.remove( t );
    }
    
    private class XtumlApplicationThread extends Thread {

        private AtomicBoolean running;
        
        public XtumlApplicationThread( String name ) {
            super( name );
            running = new AtomicBoolean( false );
        }

        @Override
        public void run() {
            running.set( true );
            while ( running.get() ) {
                // wait on a task
                try {
                    ApplicationTask task = tasks.take();
                    beforeExecute( this, task );
                    task.run();
                    afterExecute( this );
                }
                catch ( InterruptedException e ) {} // do nothing
            }
        }
        
        public void kill() {
            running.set( false );
            this.interrupt();
        }

    }


}
