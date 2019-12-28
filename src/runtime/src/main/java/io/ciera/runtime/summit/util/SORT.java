package io.ciera.runtime.summit.util;

import io.ciera.runtime.summit.exceptions.XtumlException;

public interface SORT {

    public boolean ascending(final String p_attr) throws XtumlException;
    public boolean descending(final String p_attr) throws XtumlException;

}
