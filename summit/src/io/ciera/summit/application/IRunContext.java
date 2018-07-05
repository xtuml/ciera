package io.ciera.summit.application;

import io.ciera.summit.exceptions.XtumlException;

public interface IRunContext {

    public void start();
    public void execute( IApplicationTask task );
    public IExceptionHandler getExceptionHandler();
    public void setExceptionHandler( IExceptionHandler h );
    
    public String[] args();

    public Object setSymbol( String name, Object value );
    public Object getSymbol( String name ) throws XtumlException;
    public void pushSymbolTable();
    public void popSymbolTable();

}
