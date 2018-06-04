package io.ciera.summit.types;

import java.util.UUID;

import io.ciera.summit.exceptions.XtumlException;

public class UniqueId implements IXtumlType<UniqueId>, Comparable<UniqueId> {

    UUID id;

    public UniqueId() {
        id = UUID.randomUUID();
    }

    @Override
    public int compareTo( UniqueId o ) {
        return id.compareTo( o.getId() );
    }

    public void nullify() {
        id = null;
    }

    public boolean isNull() {
        return id == null;
    }

    @Override
    public boolean equality( UniqueId value ) throws XtumlException {
        return equals( value );
    }

    @Override
    public UniqueId defaultValue() {
        return new UniqueId();
    }

    @Override
    public boolean equals( Object o ) {
        return o instanceof UniqueId && getId().equals( ((UniqueId)o).getId() );
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    private UUID getId() {
        return id;
    }

}
