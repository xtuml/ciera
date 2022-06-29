package io.ciera.runtime.api.application;

import java.io.Serializable;

import io.ciera.runtime.api.types.UniqueId;

public interface Event extends Serializable {

  int getEventId();

  String getName();

  UniqueId getEventHandle();

  Object getData(int index);
}
