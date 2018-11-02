package io.ciera.runtime.summit.application;

import io.ciera.runtime.summit.exceptions.XtumlException;

public interface IApplicationTask extends Comparable<IApplicationTask>{

    public int getPriority();
    public void run() throws XtumlException;

}
