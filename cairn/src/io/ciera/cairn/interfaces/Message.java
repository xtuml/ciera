package io.ciera.cairn.interfaces;

import io.ciera.summit.interfaces.IMessage;

public class Message implements IMessage {

    private int id;
    private Object returnValue;
    private Object[] parameterData;

    public Message( int id, Object returnValue, Object ... parameterData ) {
        this.id = id;
        this.returnValue = returnValue;
        this.parameterData = parameterData;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Object getReturnValue() {
        return returnValue;
    }

    @Override
    public Object[] getParameterData() {
        return parameterData;
    }

}
