package io.ciera.runtime.domain;

import io.ciera.runtime.api.domain.Variable;

public class LocalVariable<T> implements Variable<T> {
    
    private T value;
    
    public LocalVariable() {
    }

    public LocalVariable(T initialValue) {
        value = initialValue;
    }
    
    @Override
    public T get() {
        return value;
    }
    
    @Override
    public void set(T newValue) {
        value = newValue;
    }

}
