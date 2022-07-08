import io.ciera.runtime.api.Domain;

module io.ciera.runtime {
  exports io.ciera.runtime;

  uses Domain;

  requires transitive io.ciera.runtime.api;
  requires transitive java.logging;
}
