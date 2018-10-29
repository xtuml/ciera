package io.ciera.summit.application;

public interface IRunContext {

    public void start();
    public void execute( IApplicationTask task );
    public IExceptionHandler getExceptionHandler();
    public void setExceptionHandler( IExceptionHandler h );
    
    public String[] args();

}
