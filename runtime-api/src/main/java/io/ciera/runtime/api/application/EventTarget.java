package io.ciera.runtime.api.application;

import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.types.UniqueId;

public interface EventTarget {

  UniqueId getTargetId();

  void consumeEvent(Event event);

  ExecutionContext getContext();

  Domain getDomain();
}
