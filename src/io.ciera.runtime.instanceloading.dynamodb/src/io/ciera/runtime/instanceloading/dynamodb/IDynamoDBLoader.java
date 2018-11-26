package io.ciera.runtime.instanceloading.dynamodb;

import io.ciera.runtime.instanceloading.IPopulationLoader;

public interface IDynamoDBLoader extends IPopulationLoader {
	
	public void initialize();

}
