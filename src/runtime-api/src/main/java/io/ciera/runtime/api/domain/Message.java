package io.ciera.runtime.api.domain;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import io.ciera.runtime.api.types.UniqueId;

/**
 * A message represents an asynchronous event sent across domain boundaries. A message has a unique
 * identifier to mark which message it is as well as data items. Messages can be serialized and sent
 * across a network interface.
 */
public interface Message extends Comparable<Message>, Serializable {

  /**
   * Access the parameter datum identified by the specified key.
   *
   * @param key The string key which identifies the datum.
   * @return The parameter datum.
   */
  public Object get(String key);

  public void put(String key, Object value);

  public Map<String, Object> getParameterData();

  /**
   * Get the unique ID of this message instance.
   *
   * @return the message's unique ID.
   */
  public UniqueId getMessageHandle();

  /**
   * Get the informal name of this message instance.
   *
   * @return The name of the message.
   */
  public String getName();

  /**
   * Get the message number for this message. This number uniquely identifies an abstract message in
   * an interface.
   */
  public int getId();

  public static Message fromString(Object s) {
    throw new UnsupportedOperationException("Base 'Message' is not JSON serializable");
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public static @interface Names {
    String[] names();
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public static @interface Types {
    Class<?>[] types();
  }
}
