package io.ciera.summit.application;

import io.ciera.summit.exceptions.XtumlException;

public interface IApplicationTask extends Comparable<IApplicationTask>{
	
    public void run() throws XtumlException;

}
