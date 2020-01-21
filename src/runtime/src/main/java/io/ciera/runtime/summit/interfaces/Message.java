package io.ciera.runtime.summit.interfaces;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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
    	JSONObject msg = new JSONObject();
    	msg.put("messageHandle", getMessageHandle().serialize());
    	msg.put("name", getName());
    	msg.put("id", getId());
    	JSONArray params = new JSONArray();
    	msg.put("parameterData", params);
    	for (Object param : parameterData) {
    		if (param instanceof IXtumlType) params.put(((IXtumlType)param).serialize());
    		else params.put(param.toString());
    	}
    	return msg.toString();
    }
    
    public static IMessage deserialize(Object o) throws XtumlException {
    	if (o instanceof IMessage) {
          return (IMessage)o;
      }
    	else if (o instanceof String) {
    		JSONObject msg = new JSONObject((String)o);
    		final UniqueId messageHandle = UniqueId.deserialize(msg.get("messageHandle"));
    		final String name = msg.getString("name");
    		final int id = msg.getInt("id");
    		final List<Object> params = msg.getJSONArray("parameterData").toList();
    		return new Message() {
    			@Override
    			public UniqueId getMessageHandle() {
    				return messageHandle;
    			}
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
                    if (index >= 0 && index < params.size()) {
                        return params.get(index);
                    } else throw new XtumlException("Invalid index");
                }
    		};
    	}
        else throw new XtumlException("Cannot deserialize message");
    }
    
}
