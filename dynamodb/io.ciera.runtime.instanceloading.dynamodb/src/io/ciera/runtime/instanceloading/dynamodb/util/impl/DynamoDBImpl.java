package io.ciera.runtime.instanceloading.dynamodb.util.impl;

import io.ciera.runtime.instanceloading.dynamodb.IDynamoDBLoader;
import io.ciera.runtime.instanceloading.dynamodb.util.DynamoDB;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.util.Utility;

public class DynamoDBImpl<C extends IComponent<C>> extends Utility<C> implements DynamoDB {

    IDynamoDBLoader loader;

    public DynamoDBImpl(C context) {
        super(context);
        loader = (IDynamoDBLoader)context.getLoader("DynamoDB");
    }

    @Override
    public void load() throws XtumlException {
    	if (null != loader) {
    		loader.load();
    	}
    }

    @Override
    public void serialize() throws XtumlException {
        if (null != loader) {
            loader.serialize(context().getRunContext().getChangeLog());
        }
    }

	@Override
	public void initialize() throws XtumlException {
        if (null != loader) {
		    loader.initialize();
        }
	}

}
