package io.ciera.runtime.api.domain;

import java.io.Serializable;

import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.types.UniqueId;

public interface ObjectInstance extends InstanceActionHome, EventTarget, Serializable {

  UniqueId getInstanceId();

  void delete();

  default boolean isEmpty() {
    return this instanceof EmptyInstance;
  }

  default boolean notEmpty() {
    return !isEmpty();
  }

  boolean isActive();

  Object getIdentifier();

  Object getIdentifier(int index);

  @Override
  default UniqueId getTargetId() {
    return getInstanceId();
  }
}
