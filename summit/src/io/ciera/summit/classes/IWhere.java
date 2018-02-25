package io.ciera.summit.classes;

import io.ciera.summit.exceptions.XtumlException;

public interface IWhere {
    public boolean passes( IModelInstance candidate ) throws XtumlException;
}
