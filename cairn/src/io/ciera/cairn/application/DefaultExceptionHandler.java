package io.ciera.cairn.application;

import io.ciera.summit.application.IExceptionHandler;
import io.ciera.summit.exceptions.XtumlException;

public class DefaultExceptionHandler implements IExceptionHandler {

	@Override
	public void handle( XtumlException e ) {
		e.printStackTrace( System.err );
	}

	@Override
	public void warn( String message ) {
		System.err.println( message );
	}

}
