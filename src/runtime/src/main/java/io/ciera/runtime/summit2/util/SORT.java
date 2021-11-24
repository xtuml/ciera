package io.ciera.runtime.summit2.util;

import io.ciera.runtime.summit2.application.ExecutionContext;
import io.ciera.runtime.summit2.application.Logger;
import io.ciera.runtime.summit2.domain.Domain;
import io.ciera.runtime.summit2.domain.Utility;

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
