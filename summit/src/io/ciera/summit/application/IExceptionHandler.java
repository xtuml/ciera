package io.ciera.summit.application;

import io.ciera.summit.exceptions.XtumlException;

public interface IExceptionHandler {
	
	public void handle( XtumlException e );
	public void warn( String message );

}
