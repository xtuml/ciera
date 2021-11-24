package io.ciera.runtime.util;

import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;
import io.ciera.runtime.application.task.Halt;
import io.ciera.runtime.domain.Domain;
import io.ciera.runtime.domain.Utility;

public class ARCH extends Utility {

    public ARCH(Domain domain, ExecutionContext context, Logger logger) {
        super(domain, context, logger);
    }

    public void shutdown() {
        getContext().addTask(new Halt(getContext()));
    }

}
