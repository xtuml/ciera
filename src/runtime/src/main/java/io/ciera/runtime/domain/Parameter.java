package io.ciera.runtime.domain;

import java.util.function.Consumer;
import java.util.function.Supplier;

import io.ciera.runtime.api.domain.Variable;

public class Parameter<T> implements Variable<T> {
    
    private final Supplier<T> getter;
    private final Consumer<T> setter;
    
    public Parameter(Supplier<T> getter, Consumer<T> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public T get() {
        return getter.get();
    }

    @Override
    public void set(T newValue) {
        setter.accept(newValue);
    }

}
