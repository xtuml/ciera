package io.ciera.runtime.api;

import java.io.Serializable;
import java.util.UUID;

public interface ObjectInstance extends InstanceActionHome, EventTarget, Serializable {

  UUID getInstanceId();

  void initialize(final Domain domain);

  void delete();

  default boolean isEmpty() {
    return this instanceof EmptyInstance;
  }

  default boolean notEmpty() {
    return !isEmpty();
  }

  Object getIdentifier();
}
