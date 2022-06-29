package io.ciera.runtime.api.domain;

public interface ActionHome {

  Domain getDomain();

  default Domain getDomain(final String domainName) {
    return null;
  }
}
