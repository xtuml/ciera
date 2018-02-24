package io.ciera.summit.application;

import io.ciera.summit.components.IMessage;

public interface IReceivedMessage extends IApplicationTask {
	
	public IMessage getMessage();

}
