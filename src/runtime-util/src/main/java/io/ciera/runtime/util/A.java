package io.ciera.runtime.util;

import io.ciera.runtime.api.domain.Domain;

public class A {

	public A(Domain domain) {
	}

	public void assertTrue(final boolean truth) {
		if (truth != true) {
			throw new AssertionException("Assertion check failed");
		}
	}

	private static final class AssertionException extends RuntimeException implements Runnable {

		private static final long serialVersionUID = 1l;

        public AssertionException(String message) {
            super(message);
        }

		@Override
		public void run() {
			System.exit(2);
		}

	}

}
