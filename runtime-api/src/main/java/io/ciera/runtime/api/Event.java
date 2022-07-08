package io.ciera.runtime.api;

import java.io.Serializable;

public interface Event extends Serializable {

  int getEventId();

  String getName();

  Object getData(int index);
}
