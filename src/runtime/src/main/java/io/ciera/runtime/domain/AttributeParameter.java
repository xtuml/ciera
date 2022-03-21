package io.ciera.runtime.domain;

import java.util.function.BiConsumer;
import java.util.function.Function;

import io.ciera.runtime.api.domain.ObjectInstance;

public class AttributeParameter<C extends ObjectInstance, T> extends Parameter<T> {
    
    public AttributeParameter(C instance, Function<C, T> getter, BiConsumer<C, T> setter) {
        super(() -> getter.apply(instance), newValue -> setter.accept(instance, newValue));
    }

}
