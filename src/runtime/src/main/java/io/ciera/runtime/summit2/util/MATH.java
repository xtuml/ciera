package io.ciera.runtime.summit2.util;

import io.ciera.runtime.summit2.application.ExecutionContext;
import io.ciera.runtime.summit2.application.Logger;
import io.ciera.runtime.summit2.domain.Domain;
import io.ciera.runtime.summit2.domain.Utility;

public class MATH extends Utility {

    public MATH(Domain domain, ExecutionContext context, Logger logger) {
        super(domain, context, logger);
    }

    public double sqrt(final double x) {
        return Math.sqrt(x);
    }

}
