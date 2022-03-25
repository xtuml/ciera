package io.ciera.runtime.api.domain;

public interface Variable<T> {
    
    public T get();
    public void set(T newValue);

}
