package io.ciera.summit.classes;

import io.ciera.summit.application.IActionHome;
import io.ciera.summit.exceptions.XtumlException;

public interface IModelInstance extends IActionHome {
    
    public String getKeyLetters();
    public UniqueId getInstanceId();
    public IInstanceIdentifier getId1();
    public IInstanceIdentifier getId2();
    public IInstanceIdentifier getId3();
    public void checkLiving() throws XtumlException;
    public void delete() throws XtumlException;
    
}