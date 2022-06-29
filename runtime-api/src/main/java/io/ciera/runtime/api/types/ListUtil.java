package io.ciera.runtime.api.types;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ListUtil {

  public static <E> List<E> concat(final List<E> a, final List<E> b) {
    return Stream.concat(a.stream(), b.stream()).collect(Collectors.toList());
  }

  public static <E> List<E> concat(final List<E> a, final E b) {
    return Stream.concat(a.stream(), Stream.of(b)).collect(Collectors.toList());
  }
}
