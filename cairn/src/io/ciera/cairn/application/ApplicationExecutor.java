package io.ciera.cairn.application;

import java.util.PriorityQueue;
import java.util.Queue;

import io.ciera.summit.application.IApplicationTask;
import io.ciera.summit.application.IExceptionHandler;
import io.ciera.summit.application.IGeneratedEvent;
import io.ciera.summit.application.IPoppedTimer;
import io.ciera.summit.application.IReceivedMessage;
import io.ciera.summit.application.IRunContext;
import io.ciera.summit.exceptions.XtumlException;

public class ApplicationExecutor implements IRunContext {

	private IExceptionHandler handler;
    private Queue<IApplicationTask> tasks;
    
    public ApplicationExecutor() {
    	handler = new DefaultExceptionHandler();
    	tasks = new PriorityQueue<>();
    }

	@Override
	public void execute( Runnable command ) {
		if ( !(command instanceof IApplicationTask) ) {
			// generic code to execute
			tasks.add( new IApplicationTask() {
				@Override
				public void run() {
					command.run();
				}
				@Override
				public int compareTo( IApplicationTask o ) {
					if ( o instanceof IGeneratedEvent || o instanceof IPoppedTimer || o instanceof IReceivedMessage ) {
						return -1; // lower priority than any other type of task
					}
					else return 0; // same priority as other generic tasks
				}
			});
		}
		else tasks.add( (IApplicationTask)command );
	}
	
	@Override
	public void run() {
    	while ( !tasks.isEmpty() ) {
    		try {
    		    tasks.remove().run();
    		}
    	    catch ( XtumlException e ) {
    	    	handler.handle( e );
    	    }
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
