package io.ciera.runtime.api.application;

import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.Message;

public interface MessageTarget {

  public void deliver(Message message);

  public ExecutionContext getContext();

  public Domain getDomain();
}
