import io.ciera.runtime.api.domain.Domain;

module io.ciera.runtime {
  exports io.ciera.runtime.domain;
  exports io.ciera.runtime.application;
  exports io.ciera.runtime.time;

  uses Domain;

  requires transitive io.ciera.runtime.api;
  requires transitive java.logging;
}
