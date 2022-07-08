package io.ciera.runtime;

import java.util.Arrays;

public class InstanceIdentifier {

  public static final InstanceIdentifier EMPTY_IDENTIFIER = new InstanceIdentifier();

  private final Object[] elements;

  public InstanceIdentifier(final Object... elements) {
    this.elements = elements;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof InstanceIdentifier && elements.length > 0) {
      return Arrays.equals(elements, ((InstanceIdentifier) obj).elements);
    }
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    if (elements.length > 0) {
      return Arrays.hashCode(elements);
    }
    return super.hashCode();
  }
}
