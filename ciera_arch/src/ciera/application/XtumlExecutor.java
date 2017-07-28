package ciera.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import ciera.exceptions.XtumlException;

public class XtumlExecutor implements Executor {

    private BlockingQueue<XtumlTask> tasks;
    private Map<Thread, XtumlTask> taskMap;
    private boolean setNumberOfThreads;
    private boolean running;
    
    private List<XtumlApplicationThread> threads;
    
    public XtumlExecutor() {
        setNumberOfThreads = false;
        running = false;
        tasks = new LinkedBlockingQueue<XtumlTask>();
        taskMap = new ConcurrentHashMap<Thread, XtumlTask>();
        threads = new ArrayList<XtumlApplicationThread>( 1 );
        threads.add( new XtumlApplicationThread() );
    }

    @Override
    public void execute( Runnable command ) {
        if ( null != command && command instanceof XtumlTask ) {
            tasks.add( (XtumlTask)command );
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
            for ( int i = 0; i < size; i++ ) threads.add( new XtumlApplicationThread() );
        }
    }
    
    public void addInitialTask( XtumlTask task ) {
        if ( !running ) tasks.add( task );
    }

    public XtumlTask getCurrentTask() {
        return taskMap.get( Thread.currentThread() );
    }
    
    private void beforeExecute( Thread t, XtumlTask task ) {
        taskMap.put( t, task );
    }
    
    private void afterExecute( Thread t ) {
        taskMap.remove( t );
    }
    
    private class XtumlApplicationThread extends Thread {

        private AtomicBoolean running;
        
        public XtumlApplicationThread() {
            running = new AtomicBoolean( false );
        }

        @Override
        public void run() {
            running.set( true );
            while ( running.get() ) {
                // wait on a task
                try {
                    XtumlTask task = tasks.take();
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
