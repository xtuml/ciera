package io.ciera.tool.dynamodb.ees.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.util.Utility;
import io.ciera.tool.dynamodb.ees.C_UTIL;

public class C_UTILImpl<C extends IComponent<C>> extends Utility<C> implements C_UTIL {

    public C_UTILImpl(C context) {
        super(context);
    }

    public String organizeImports(final String p_s) {
        // split into lines and sort them into a set
        Set<String> lines = new ImportSet(p_s.split("\\r?\\n"));
        // build return string
        String returnString = "";
        String prevBase = "";
        for (String line : lines) {
            if (line.trim().isEmpty())
                break;
            int dotIndex = line.indexOf('.');
            if (-1 == dotIndex)
                dotIndex = line.indexOf(';');
            if (!prevBase.equals(line.substring(0, dotIndex)) && !prevBase.isEmpty()) {
                returnString += "\n";
            }
            returnString += line + "\n";
            prevBase = line.substring(0, dotIndex);
        }
        return returnString;
    }

    public String stripTics(final String p_s) {
        return p_s.replaceAll("'", "");
    }

    @SuppressWarnings("serial")
    private class ImportSet extends TreeSet<String> {

        public ImportSet(String[] elems) {
            this(Arrays.asList(elems));
        }

        public ImportSet(Collection<String> c) {
            super(c);
        }

        @Override
        public boolean add(String e) {
            if (null != e && !"".equals(e.trim()))
                return super.add(e);
            else
                return false;
        }
    }

}
