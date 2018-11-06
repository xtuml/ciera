package io.ciera.runtime.summit.classes;

import io.ciera.runtime.summit.application.IActionHome;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.IEventTarget;
import io.ciera.runtime.summit.types.IXtumlType;
import io.ciera.runtime.summit.types.UniqueId;

public interface IModelInstance<T extends IModelInstance<T, C>, C extends IComponent<C>>
        extends IActionHome<C>, IXtumlType<T>, IEventTarget {

    public String getKeyLetters();

    public UniqueId getInstanceId();

    public IInstanceIdentifier getId1();

    public IInstanceIdentifier getId2();

    public IInstanceIdentifier getId3();

    public void checkLiving() throws XtumlException;

    public void delete() throws XtumlException;

    default public boolean isEmpty() {
        return false;
    }

}
