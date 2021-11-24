package io.ciera.runtime.summit2.util;

import io.ciera.runtime.summit2.application.ExecutionContext;
import io.ciera.runtime.summit2.application.Logger;
import io.ciera.runtime.summit2.application.task.Halt;
import io.ciera.runtime.summit2.domain.Domain;
import io.ciera.runtime.summit2.domain.Utility;

public class ARCH extends Utility {

    public ARCH(Domain domain, ExecutionContext context, Logger logger) {
        super(domain, context, logger);
    }

    public void shutdown() {
        getContext().addTask(new Halt(getContext()));
    }

}
