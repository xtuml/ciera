package io.ciera.runtime;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class MemberParameter<C, T> extends Parameter<T> {

  public MemberParameter(
      final C instance, final Function<C, T> getter, final BiConsumer<C, T> setter) {
    super(() -> getter.apply(instance), newValue -> setter.accept(instance, newValue));
  }
}
