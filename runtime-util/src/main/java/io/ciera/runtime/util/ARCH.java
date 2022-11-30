package io.ciera.runtime.util;

import io.ciera.runtime.api.Architecture;

public class ARCH {

  private final Architecture arch = Architecture.getInstance();

  public ARCH(Object domain) {}

  public void shutdown() {
    arch.shutdown();
  }
}
