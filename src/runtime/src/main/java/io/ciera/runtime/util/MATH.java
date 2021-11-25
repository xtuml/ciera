package io.ciera.runtime.util;

import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.domain.Domain;
import io.ciera.runtime.domain.Utility;

public class MATH extends Utility {

    public MATH(Domain domain) {
        super(domain);
    }

    public MATH(Domain domain, ExecutionContext context) {
        super(domain, context);
    }

    public double sqrt(final double x) {
        return Math.sqrt(x);
    }

}
