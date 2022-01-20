package io.ciera.runtime.util;

import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.application.task.Halt;

public class ARCH {

    private final Domain domain;

    public ARCH(Domain domain) {
        this.domain = domain;
    }

    public void shutdown() {
        domain.getContext().execute(new Halt());
    }

}
