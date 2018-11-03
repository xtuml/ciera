package io.ciera.runtime.summit.interfaces;

public class Message implements IMessage {

    private String name;
    private Object[] parameterData;

    public Message(String name, Object... parameterData) {
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
