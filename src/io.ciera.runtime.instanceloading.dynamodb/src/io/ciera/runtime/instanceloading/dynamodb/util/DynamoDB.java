package io.ciera.runtime.instanceloading.dynamodb.util;

import io.ciera.runtime.summit.exceptions.XtumlException;

public interface DynamoDB {

    public void load() throws XtumlException;

    public void serialize() throws XtumlException;

    public void initialize() throws XtumlException;

}
