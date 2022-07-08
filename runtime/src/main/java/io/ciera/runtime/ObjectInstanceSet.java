package io.ciera.runtime;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import io.ciera.runtime.api.ObjectInstance;

final class ObjectInstanceSet extends LinkedHashSet<ObjectInstance> {

  private static final long serialVersionUID = 1L;

  private final Set<Object> primaryIdentifierSet;

  ObjectInstanceSet() {
    primaryIdentifierSet = new HashSet<>();
  }

  @Override
  public boolean add(final ObjectInstance e) {
    final Object identifier = e.getIdentifier();
    return (InstanceIdentifier.EMPTY_IDENTIFIER.equals(identifier)
            || primaryIdentifierSet.add(identifier))
        && super.add(e);
  }

  @Override
  public boolean remove(final Object o) {
    if (o instanceof ObjectInstance) {
      primaryIdentifierSet.remove(((ObjectInstance) o).getIdentifier());
    }
    return super.remove(o);
  }

  @Override
  public void clear() {
    primaryIdentifierSet.clear();
    super.clear();
  }
}
