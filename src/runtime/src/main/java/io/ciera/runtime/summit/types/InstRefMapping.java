package io.ciera.runtime.summit.types;

public abstract class InstRefMapping implements IXtumlType {

    private Object value;

    public InstRefMapping() {
    }

    public InstRefMapping(Object value) {
        this.value = value;
    }

}
