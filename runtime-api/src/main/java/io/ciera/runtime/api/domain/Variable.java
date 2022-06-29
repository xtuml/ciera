package io.ciera.runtime.api.domain;

public interface Variable<T> {

  T get();

  void set(T newValue);
}
