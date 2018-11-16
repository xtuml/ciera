package io.ciera.runtime.summit.interfaces;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.IXtumlType;
import io.ciera.runtime.summit.types.UniqueId;

public abstract class Message implements IMessage, Comparable<IMessage> {
	
	public static final int NULL_SIGNAL = 0;
	
	private UniqueId messageHandle;
    private Object[] parameterData;
    
    public Message() {
    	this(new Object[0]);
    }

    public Message(Object... data) {
    	messageHandle = UniqueId.random();
        parameterData = data;
    }

    @Override
    public Object get(int index) throws XtumlException {
        if (index >= 0 && index < parameterData.length) {
            return parameterData[index];
        } else
            throw new XtumlException("Invalid index");
    }
    
    @Override
    public UniqueId getMessageHandle() {
    	return messageHandle;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public int compareTo(IMessage m) {
    	return messageHandle.compareTo(m.getMessageHandle());
    }
    
    @Override
    public boolean equals(Object o) {
    	if (null != o && o instanceof IMessage) return messageHandle.equals(((IMessage)o).getMessageHandle());
    	else return false;
    }
    
    @Override
    public int hashCode() {
    	return messageHandle.hashCode();
    }
    
    @Override
    public String serialize() throws XtumlException {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	PrintStream out = new PrintStream(baos);
    	out.printf("%d,%s", getId(), getName());
    	for (Object param : parameterData) {
    		out.print(',');
    		if (param instanceof IXtumlType<?>) out.print(((IXtumlType<?>)param).serialize());
    		else out.print(param.toString());
    	}
    	out.println();
    	return baos.toString();
    }
    
    public static IMessage deserialize(Object o) throws XtumlException {
    	if (o instanceof String) {
    		String[] parts = ((String)o).split(",");
    		if (parts.length >= 2) {
    			final int id = Integer.parseInt(parts[0]);
    			final String name = parts[1];
    			final String[] params = Arrays.copyOfRange(parts, 2, parts.length);
    			return new Message() {
                    @Override
                    public String getName() {
                    	return name;
                    }
                    @Override
                    public int getId() {
                    	return id;
                    }
                    @Override
                    public Object get(int index) throws XtumlException {
                        if (index >= 0 && index < params.length) {
                            return params[index];
                        } else throw new XtumlException("Invalid index");
                    }
    			};
    		}
    		else throw new XtumlException("Cannot deserialize message");
    	}
        else throw new XtumlException("Cannot deserialize message");
    }
    
}
