package io.ciera.runtime.util;

import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;
import io.ciera.runtime.domain.Domain;
import io.ciera.runtime.domain.Utility;

public class SORT extends Utility {

    public SORT(Domain domain, ExecutionContext context, Logger logger) {
        super(domain, context, logger);
    }

    public boolean ascending(final String attr) {
        return true;
    }

    public boolean descending(final String attr) {
        return true;
    }

}
