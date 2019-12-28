package io.ciera.runtime.instanceloading;

import java.util.List;

public interface IChangeLog {
	
	public List<IModelDelta> getChanges();
	public void addChange(IModelDelta delta);

}
