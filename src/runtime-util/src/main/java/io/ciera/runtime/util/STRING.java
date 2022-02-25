package io.ciera.runtime.util;

import io.ciera.runtime.api.domain.Domain;

public class STRING {

    public STRING(Domain domain) {
    }

    public String itoa(final int i) {
        return Integer.toString(i);
    }

    public int atoi(final String s) {
        return Integer.parseInt(s);
    }

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

    public int strlen(final String s) {
        return s.length();
    }

    public int indexof(final String haystack, final String needle) {
        return haystack.indexOf(needle);
    }

    public String trim(final String s) {
        return s.trim();
    }

    public String quote() {
        return "\"";
    }

    public String escapetics(final String s) {
        return s.replaceAll("'", "''");
    }

    public String unescapetics(final String s) {
        return s.replaceAll("''", "'");
    }

    @Override
    public String replaceall(final String s, final String pattern, final String replacement) {
        return s.replaceAll(pattern, replacement);
    }

}
