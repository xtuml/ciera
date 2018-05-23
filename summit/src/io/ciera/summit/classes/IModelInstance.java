package io.ciera.summit.classes;

import io.ciera.summit.application.IActionHome;
import io.ciera.summit.components.IComponent;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.IXtumlType;
import io.ciera.summit.types.IUniqueId;;

public interface IModelInstance<T extends IModelInstance<T,C>,C extends IComponent<C>> extends IActionHome<C>, IXtumlType<T> {
    
	public String getKeyLetters();
    public IUniqueId getInstanceId();
    public IInstanceIdentifier getId1();
    public IInstanceIdentifier getId2();
    public IInstanceIdentifier getId3();
    public void checkLiving() throws XtumlException;
    public void delete() throws XtumlException;
    
}