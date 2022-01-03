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
    public String itoa(final int i) {
        return Integer.toString(i);
    }

    @Override
    public int atoi(final String s) throws XtumlException {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new XtumlException(e);
        }
    }

    @Override
    public String substr(final String s, final int begin, final int end) {
        int b = begin;
        int e = end;
        if ((b > s.length() - 1) || (e >= 0 && e <= b))
            return "";
        if (b < 0)
            b = 0;
        if (e < 0 || e > s.length())
            return s.substring(b);
        else
            return s.substring(b, e);
    }

    @Override
    public int strlen(final String s) {
        return s.length();
    }

    @Override
    public int indexof(final String haystack, final String needle) {
        return haystack.indexOf(needle);
    }

    @Override
    public String trim(final String s) {
        return s.trim();
    }

    @Override
    public String quote() {
        return "\"";
    }

    @Override
    public String escapetics(final String s) {
        return s.replaceAll("'", "''");
    }

    @Override
    public String unescapetics(final String s) {
        return s.replaceAll("''", "'");
    }

    @Override
    public String replaceall(final String s, final String pattern, final String replacement) {
        return s.replaceAll(pattern, replacement);
    }

}
