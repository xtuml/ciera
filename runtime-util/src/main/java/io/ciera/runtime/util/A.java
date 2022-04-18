package io.ciera.runtime.util;

import io.ciera.runtime.api.domain.Domain;

public class A {

  public A(Domain domain) {}

  public void assertTrue(final boolean truth) {
    assertTrueWithMessage(truth, "Assertion check failed");
  }

  public void assertTrueWithMessage(final boolean truth, final String message) {
    if (truth != true) {
      throw new AssertionException(message);
    }
  }

  private static final class AssertionException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public AssertionException(String message) {
      super(message);
    }
  }
}
