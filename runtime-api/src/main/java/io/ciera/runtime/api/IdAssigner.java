package io.ciera.runtime.api;

import java.util.UUID;
import java.util.function.Supplier;

public interface IdAssigner extends Supplier<UUID> {}
