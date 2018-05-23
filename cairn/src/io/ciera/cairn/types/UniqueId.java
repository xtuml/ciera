package io.ciera.cairn.types;

import java.util.UUID;

import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.IUniqueId;
import io.ciera.summit.types.IXtumlType;

public class UniqueId implements IUniqueId {

    UUID id;

    public UniqueId() {
        id = UUID.randomUUID();
    }

    @Override
    public int compareTo( IUniqueId o ) {
        if ( o instanceof UniqueId ) return id.compareTo( ((UniqueId)o).getId() );
        else return 0;
    }

    @Override
    public void nullify() {
        id = null;
    }

    @Override
    public boolean isNull() {
        return id == null;
    }

    @Override
    public boolean equality( IXtumlType<IUniqueId> value ) throws XtumlException {
        return equals( value );
    }

    @Override
    public IUniqueId defaultValue() {
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

    protected UUID getId() {
        return id;
    }

}
