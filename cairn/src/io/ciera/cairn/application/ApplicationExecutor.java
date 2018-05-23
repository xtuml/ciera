package io.ciera.cairn.application;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import io.ciera.cairn.application.tasks.HaltExecutionTask;
import io.ciera.summit.application.IApplicationTask;
import io.ciera.summit.application.IExceptionHandler;
import io.ciera.summit.application.IRunContext;
import io.ciera.summit.exceptions.XtumlException;

public class ApplicationExecutor extends Thread implements IRunContext {

    private IExceptionHandler handler;
    private BlockingQueue<IApplicationTask> tasks;
    private boolean running;

    public ApplicationExecutor( String name ) {
        super( name );
        handler = new DefaultExceptionHandler();
        tasks = new PriorityBlockingQueue<>();
        running = false;
    }

    @Override
    public void execute( IApplicationTask task ) {
        tasks.add( task );
    }

    @Override
    public void run() {
        running = true;
        while ( running ) {
            try {
                IApplicationTask task = tasks.take();
                if ( task instanceof HaltExecutionTask ) {
                    running = false;
                }
                else {
                    try {
                      task.run();
                    }
                    catch ( XtumlException e ) {
                      handler.handle( e );
                    }
                }
            }
            catch ( InterruptedException e ) {}
        }
    }

    @Override
    public IExceptionHandler getExceptionHandler() {
        return handler;
    }

    @Override
    public void setExceptionHandler( IExceptionHandler h ) {
        if ( null != h ) handler = h;
    }

}
