package io.ciera.runtime.application;

import java.util.Optional;
import java.util.stream.Stream;

import io.ciera.runtime.api.application.ExceptionHandler;

public class DefaultExceptionHandler implements ExceptionHandler {

	@Override
	public void handleError(RuntimeException e) {
		e.printStackTrace();
		// run actions associated with the error
		getExceptionActions(e).forEach(Runnable::run);
		if (e instanceof Runnable) {
			((Runnable) e).run();
		}
		System.exit(1);
	}

	@Override
	public void handleSoftError(RuntimeException e) {
		throw e;
	}

	private Stream<Runnable> getExceptionActions(Throwable e) {
		return Stream.concat(Optional.ofNullable(e).stream().map(Throwable::getCause).flatMap(this::getExceptionActions),
				Stream.of(e)).filter(e2 -> e2 instanceof Runnable).map(Runnable.class::cast);
	}

}
