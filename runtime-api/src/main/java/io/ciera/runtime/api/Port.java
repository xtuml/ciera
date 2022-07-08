package io.ciera.runtime.api;

public interface Port extends ActionHome, MessageTarget {

  String getName();

  void send(Message message);

  void setPeer(MessageTarget peer);

  MessageTarget getPeer();

  boolean satisfied();
}
