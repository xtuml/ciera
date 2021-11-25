package io.ciera.runtime.util;

import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.task.Halt;
import io.ciera.runtime.domain.Domain;
import io.ciera.runtime.domain.Utility;

public class ARCH extends Utility {

    public ARCH(Domain domain) {
        super(domain);
    }

    public ARCH(Domain domain, ExecutionContext context) {
        super(domain, context);
    }

    public void shutdown() {
        getContext().addTask(new Halt(getContext()));
    }

}
