package io.ciera.runtime.api.application;

import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.Message;

public interface MessageTarget {

  void deliver(Message message);

  ExecutionContext getContext();

  Domain getDomain();
}
