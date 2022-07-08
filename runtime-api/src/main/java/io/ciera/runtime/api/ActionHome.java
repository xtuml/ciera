package io.ciera.runtime.api;

import java.util.function.Function;

public interface ActionHome {

  <E extends Event> void generateEvent(
      Function<Object[], E> eventBuilder, EventTarget target, Object... data);

  Domain getDomain();

  Domain getDomain(final String domainName);
}
