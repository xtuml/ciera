package io.ciera.runtime.summit.interfaces;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;
import org.json.JSONObject;

public interface IMessage {

	public UniqueId getMessageHandle();
	
    public String getName();

    public int getId();

    public Object get(int index) throws XtumlException;
    
    public String serialize() throws XtumlException;

    public void addParm(String key, Object value) throws XtumlException;

    public Object getParm(String key);
    
    public JSONObject getParms();
}
