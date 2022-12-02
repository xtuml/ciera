package io.ciera.runtime.api;

public interface Port extends ActionHome {

  void setPeer(Port peer);

  Port getPeer();

  default boolean satisfied() {
    return getPeer() != null;
  }
}
