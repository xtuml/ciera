package io.ciera.runtime.api.domain;

public interface TransitionRule {

  Enum<?> execute();
}
