package io.ciera.runtime.summit2.domain;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.ciera.runtime.summit2.exceptions.DeserializationException;
import io.ciera.runtime.summit2.types.MessageHandle;

/**
 * The SerializableMessage class extends {@link Message} with the ability to
 * dump and parse the message to a JSON string for the purpose of passing the
 * message across some sort of interface between deployed processes.
 */
public class SerializableMessage extends Message {

    public static final String MESSAGE_HANDLE = "messageHandle";
    public static final String MESSAGE_NAME = "name";
    public static final String MESSAGE_ID = "id";
    public static final String MESSAGE_PARAMETER_DATA = "parameterData";

    public SerializableMessage() {
        super();
    }

    public SerializableMessage(MessageHandle messageHandle, String name, int id, Object... data) {
        super(messageHandle, name, id, data);
    }

    @Override
    public String toString() {
        JSONObject msg = new JSONObject();
        msg.put(MESSAGE_HANDLE, getMessageHandle());
        msg.put(MESSAGE_NAME, getName());
        msg.put(MESSAGE_ID, getId());
        JSONArray params = new JSONArray();
        msg.put(MESSAGE_PARAMETER_DATA, params);
        for (Object param : getAll()) {
            params.put(param);
        }
        return msg.toString();
    }

    public static Message fromString(Object s) {
        try {
            JSONObject msg = new JSONObject(s);
            MessageHandle messageHandle = MessageHandle.fromString(msg.getString(MESSAGE_HANDLE));
            String name = msg.getString(MESSAGE_NAME);
            int id = msg.getInt(MESSAGE_ID);
            List<Object> params = msg.getJSONArray(MESSAGE_PARAMETER_DATA).toList();
            return new SerializableMessage(messageHandle, name, id, params);
        } catch (JSONException e) {
            throw new DeserializationException("Could not parse JSON 'Message' instance", e);
        }
    }

}