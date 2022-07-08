package io.ciera.runtime;

import java.util.function.Consumer;
import java.util.function.Supplier;

import io.ciera.runtime.api.Variable;

public class Parameter<T> implements Variable<T> {

  private final Supplier<T> getter;
  private final Consumer<T> setter;

  public Parameter(final Supplier<T> getter, final Consumer<T> setter) {
    this.getter = getter;
    this.setter = setter;
  }

  @Override
  public T get() {
    return getter.get();
  }

  @Override
  public void set(final T newValue) {
    setter.accept(newValue);
  }
}
