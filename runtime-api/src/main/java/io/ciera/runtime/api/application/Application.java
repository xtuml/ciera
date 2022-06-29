package io.ciera.runtime.api.application;

import java.util.Collection;
import java.util.ServiceLoader;
import java.util.stream.Stream;

import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.time.SystemClock;

public interface Application {

  String getName();

  SystemClock getClock();

  void setClock(SystemClock clock);

  void setup();

  void initialize();

  void start();

  void stop();

  Collection<? extends ExecutionContext> getContexts();

  <T extends ExecutionContext> T getContext(String name);

  ExecutionContext defaultContext();

  void addContext(ExecutionContext context);

  Collection<Domain> getDomains();

  Domain getDomain(String domainName);

  void addDomain(Domain domain);

  Stream<Domain> findDomains(String... domainNames);

  Logger getLogger();

  void setLogger(Logger logger);

  ExceptionHandler getExceptionHandler();

  void setExceptionHandler(ExceptionHandler exceptionHandler);

  boolean isRunning();

  static Application getInstance(final String name) {
    return ServiceLoader.load(Application.class).stream()
        .map(ServiceLoader.Provider::get)
        .filter(app -> app.getName().equals(name))
        .findAny()
        .orElseThrow();
  }

  static Application getInstance() {
    return ServiceLoader.load(Application.class).findFirst().orElseThrow();
  }
}
