package io.ciera.cairn.application;

import io.ciera.summit.application.IExceptionHandler;
import io.ciera.summit.exceptions.XtumlException;

public class DefaultExceptionHandler implements IExceptionHandler {

	@Override
	public void handle( XtumlException e ) {
		System.err.println( e.getMessage() );
		System.err.println( e.getStackTrace() );
	}

}
