package io.ciera.runtime.domain;

import java.util.Map;
import java.util.Optional;
import io.ciera.runtime.application.Named;
import io.ciera.runtime.exceptions.DeserializationException;
import io.ciera.runtime.types.UniqueId;

/**
 * A message represents an asynchronous event sent across domain boundaries. A
 * message has a unique identifier to mark which message it is as well as data
 * items. Messages can be serialized and sent across a network interface.
 */
public class Message implements Comparable<Message>, Named {
    
    public static final int NULL_SIGNAL = 0;

    private final UniqueId messageHandle;
    private final int messageId;
    private final String name;
    private final Map<String, Object> parameterData;

    public Message() {
        this(new UniqueId(), NULL_SIGNAL, null, Map.<String, Object>of());
    }

    public Message(int id) {
        this(id, Map.<String, Object>of());
    }

    public Message(int id, Map<String, Object> parameterData) {
        this(UniqueId.random(), id, null, parameterData);
    }

    public Message(UniqueId messageHandle, int id, String name, Map<String, Object> parameterData) {
        this.messageHandle = messageHandle;
        this.messageId = id;
        this.name = name;
        this.parameterData = parameterData;
    }

    /**
     * Access the parameter datum at the specified index.
     * 
     * @param index The index at which to access the datum.
     * @return The parameter datum.
     */
    public Object get(String key) {
        return Optional.of(parameterData.get(key)).orElseThrow();
    }
    
    public void put(String key, Object value) {
        parameterData.put(key, value);
    }
    
    public Map<String, Object> getParameterData() {
        return parameterData;
    }

    /**
     * Get the unique ID of this message instance.
     * 
     * @return the message's unique ID.
     */
    public UniqueId getMessageHandle() {
        return messageHandle;
    }

    /**
     * Get the informal name of this message instance.
     * 
     * @return The name of the message.
     */
    @Override
    public String getName() {
        return name != null ? name : getClass().getSimpleName();
    }

    /**
     * Get the message number for this message. This number uniquely identifies an
     * abstract message in an interface.
     */
    public int getId() {
        return messageId;
    }

    @Override
    public int compareTo(Message m) {
        return messageHandle.compareTo(m.messageHandle);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Message && messageHandle.equals(((Message) o).messageHandle);
    }

    @Override
    public int hashCode() {
        return messageHandle.hashCode();
    }

    public static Message fromString(Object s) {
        throw new DeserializationException("Base 'Message' is not JSON serializable.");
    }

    @Override
    public String toString() {
        return String.format("%s[%.8s]", getName(), messageHandle);
    }
    
}
