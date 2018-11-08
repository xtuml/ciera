package io.ciera.runtime.summit.statemachine;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.IXtumlType;

public interface IEvent extends IXtumlType<IEvent> {

    public String getName();

    public int getId();

    public Object get(int index) throws XtumlException;

    public IEventTarget getTarget();

    public boolean toSelf();

    public IEvent to(IEventTarget target);

    public IEvent toSelf(IEventTarget target);

}
