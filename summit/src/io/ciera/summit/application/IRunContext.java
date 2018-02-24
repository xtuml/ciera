package io.ciera.summit.application;

import java.util.concurrent.Executor;

public interface IRunContext extends Executor, Runnable {
	
	public IExceptionHandler getExceptionHandler();
	public void setExceptionHandler( IExceptionHandler h );

}
