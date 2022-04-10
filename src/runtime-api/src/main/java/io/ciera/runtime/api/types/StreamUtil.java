package io.ciera.runtime.api.types;

import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class StreamUtil {

    public static <T> Collector<T, ?, Optional<T>> findOnly() {
        return Collectors.collectingAndThen(Collectors.toList(), list -> {
            if (list.size() == 0) {
                return Optional.empty();
            } else if (list.size() == 1) {
                return Optional.of(list.get(0));
            } else {
                throw new RuntimeException("Find expression resulted in more than one element");
            }
        });
    }

}
