package io.ciera.runtime.api.domain;

import java.io.Serializable;

import io.ciera.runtime.api.action.InstanceActionHome;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.types.UniqueId;

public interface ObjectInstance extends InstanceActionHome, EventTarget, Serializable {

  public UniqueId getInstanceId();

  public void delete();

  public boolean isEmpty();

  public default boolean notEmpty() {
    return !isEmpty();
  }

  public boolean isActive();

  public Object getIdentifier();

  public Object getIdentifier(int index);

  @Override
  public default UniqueId getTargetId() {
    return getInstanceId();
  }
}
