package io.ciera.runtime.summit.util.impl;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;
import io.ciera.runtime.summit.util.STRING;
import io.ciera.runtime.summit.util.Utility;

public class STRINGImpl<C extends IComponent<C>> extends Utility<C> implements STRING {

    public STRINGImpl(C context) {
        super(context);
    }

    @Override
    public String itoa(final int p_i) throws XtumlException {
        return Integer.toString(p_i);
    }

    @Override
    public int atoi(final String p_s) throws XtumlException {
        return Integer.parseInt(p_s);
    }

    @Override
    public String substr(final String p_s, final int p_begin, final int p_end) throws XtumlException {
        // if begin < 0, returns a substring starting at the beginning of s
        // if begin > the length of s - 1, returns an empty string
        // if end < 0 or > the length of s, returns a substring starting at begin to the
        // end of s
        // if end <= begin, returns empty string
        int begin = p_begin;
        int end = p_end;
        if ((begin > p_s.length() - 1) || (end >= 0 && end <= begin))
            return "";
        if (begin < 0)
            begin = 0;
        if (end < 0 || end > p_s.length())
            return p_s.substring(begin);
        else
            return p_s.substring(begin, end);
    }

    @Override
    public int strlen(final String p_s) throws XtumlException {
        return p_s.length();
    }

    @Override
    public int indexof(final String p_haystack, final String p_needle) throws XtumlException {
        return p_haystack.indexOf(p_needle);
    }

    @Override
    public String getword(final String p_s, final int p_i, final int p_j) throws XtumlException {
        // Insert your implementation here
        throw new XtumlException("Bridge not implemented");
    }

    @Override
    public String trim(final String p_s) throws XtumlException {
        return p_s.trim();
    }

    @Override
    public String quote() throws XtumlException {
        return "\"";
    }

    @Override
    public String escapetics(final String p_s) throws XtumlException {
        return p_s.replaceAll("'", "''");
    }

    @Override
    public String unescapetics(final String p_s) throws XtumlException {
        return p_s.replaceAll("''", "'");
    }

    @Override
    public String idtoa(final String p_a, final UniqueId p_id) throws XtumlException {
        // Insert your implementation here
        throw new XtumlException("Bridge not implemented");
    }

}
