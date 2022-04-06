package io.ciera.runtime.domain;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class MemberParameter<C, T> extends Parameter<T> {
    
    public MemberParameter(C instance, Function<C, T> getter, BiConsumer<C, T> setter) {
        super(() -> getter.apply(instance), newValue -> setter.accept(instance, newValue));
    }

}
