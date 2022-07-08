package io.ciera.runtime.api;

public interface Variable<T> {

  T get();

  void set(T newValue);
}
