package io.ciera.runtime.summit.util;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;

public interface STRING {

    public String itoa( final int p_i ) throws XtumlException;
    public int atoi( final String p_s ) throws XtumlException;
    public String substr( final String p_s,  final int p_begin,  final int p_end ) throws XtumlException;
    public int strlen( final String p_s ) throws XtumlException;
    public int indexof( final String p_haystack,  final String p_needle ) throws XtumlException;
    public String getword( final String p_s,  final int p_i,  final int p_j ) throws XtumlException;
    public String trim( final String p_s ) throws XtumlException;
    public String quote() throws XtumlException;
    public String escapetics( final String p_s ) throws XtumlException;
    public String unescapetics( final String p_s ) throws XtumlException;
    public String idtoa( final String p_a,  final UniqueId p_id ) throws XtumlException;

}
