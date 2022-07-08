package io.ciera.runtime.api;

public interface MessageTarget {

  void deliver(Message message);
}
