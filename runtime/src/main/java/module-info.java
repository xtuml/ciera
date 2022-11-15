import io.ciera.runtime.SimulatedClock;
import io.ciera.runtime.api.SystemClock;

module io.ciera.runtime {
  exports io.ciera.runtime;

  provides SystemClock with
      SimulatedClock;

  requires transitive io.ciera.runtime.api;
  requires transitive java.logging;
}
