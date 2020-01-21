package io.ciera.runtime.summit.classes;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.ISet;
import io.ciera.runtime.summit.types.UniqueId;

public interface IInstanceSet<S extends IInstanceSet<S, E>, E extends IModelInstance<E, ?>> extends ISet<E> {

    public E getByInstanceId(UniqueId instanceId) throws XtumlException;

    public E getById1(IInstanceIdentifier id1) throws XtumlException;

    public E getById2(IInstanceIdentifier id2) throws XtumlException;

    public E getById3(IInstanceIdentifier id3) throws XtumlException;

}
