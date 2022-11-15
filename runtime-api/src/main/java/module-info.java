import io.ciera.runtime.api.IdAssigner;
import io.ciera.runtime.api.SystemClock;

module io.ciera.runtime.api {
  exports io.ciera.runtime.api.types;
  exports io.ciera.runtime.api;
  exports io.ciera.runtime.api.exceptions;

  uses IdAssigner;
  uses SystemClock;
}
