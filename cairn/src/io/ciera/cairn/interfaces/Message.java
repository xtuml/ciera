package io.ciera.cairn.interfaces;

import io.ciera.summit.interfaces.IMessage;

public class Message implements IMessage {

    private String name;
    private Object[] parameterData;

    public Message( String name, Object ... parameterData ) {
        this.name = name;
        this.parameterData = parameterData;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object[] getParameterValues() {
        return parameterData;
    }

}
