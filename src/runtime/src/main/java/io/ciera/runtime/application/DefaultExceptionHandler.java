package io.ciera.runtime.application;

import java.util.Optional;
import java.util.stream.Stream;

import io.ciera.runtime.api.application.ExceptionHandler;

public class DefaultExceptionHandler implements ExceptionHandler {

	@Override
	public void handleError(RuntimeException e) {
		e.printStackTrace();
		System.exit(1);
	}

	@Override
	public void handleSoftError(RuntimeException e) {
		throw e;
	}

}
