package io.ciera.runtime.util;

import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.domain.Domain;
import io.ciera.runtime.domain.Utility;

public class SORT extends Utility {

    public SORT(Domain domain) {
        super(domain);
    }

    public SORT(Domain domain, ExecutionContext context) {
        super(domain, context);
    }

    public boolean ascending(final String attr) {
        return true;
    }

    public boolean descending(final String attr) {
        return true;
    }

}
