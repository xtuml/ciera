package io.ciera.runtime.summit.classes;

import io.ciera.runtime.summit.application.IInstanceActionHome;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.IEventTarget;
import io.ciera.runtime.summit.types.IXtumlType;
import io.ciera.runtime.summit.types.UniqueId;

public interface IModelInstance<T extends IModelInstance<T, C>, C extends IComponent<C>>
        extends IInstanceActionHome<T, C>, IXtumlType, IEventTarget {

    public String getKeyLetters();

    public UniqueId getInstanceId();

    public IInstanceIdentifier getId1();

    public IInstanceIdentifier getId2();

    public IInstanceIdentifier getId3();

    public void checkLiving() throws XtumlException;
    
    public boolean isAlive();

    public void delete() throws XtumlException;

    public int compareTo(T o);

    default public boolean isEmpty() {
        return false;
    }

}
