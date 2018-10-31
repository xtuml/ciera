package io.ciera.template.rsl.ees.impl;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.util.Utility;
import io.ciera.template.rsl.ees.STR;

public class STRImpl<C extends IComponent<C>> extends Utility<C> implements STR {
    public STRImpl( C context ) {
        super( context );
    }
    public String itoa( final int p_i ) {
        return Integer.toString(p_i);
    }
    public int atoi( final String p_s ) {
        return Integer.parseInt(p_s);
    }
    public String substr( final String p_s,  final int p_begin,  final int p_end ) {
        //  if begin < 0, returns a substring starting at the beginning of s
        //  if begin > the length of s - 1, returns an empty string
        //  if end < 0 or > the length of s, returns a substring starting at begin to the end of s
        //  if end <= begin, returns empty string
        int begin = p_begin;
        int end = p_end;
       // System.out.println(begin);
       // System.out.println(end);
       // if ( !p_s.contains("\n") ) {
       //     System.out.println(p_s);
       // }
        if ( ( begin > p_s.length() - 1 ) || ( end >= 0 && end <= begin ) ) return "";
        if ( begin < 0 ) begin = 0;
        if ( end < 0 || end > p_s.length() ) return p_s.substring( begin );
        else return p_s.substring( begin, end );
    }
    public int strlen( final String p_s ) {
        return p_s.length();
    }
    public int indexof( final String p_haystack,  final String p_needle ) {
        return p_haystack.indexOf( p_needle );
    }
    public String getword( final String p_s,  final int p_i,  final int p_j ) {
        // Insert your implementation here
        return "";
    }
    public String trim( final String p_s ) {
        return p_s.trim();
    }
    public String quote() {
        return "\"";
    }
    public String escapetics( final String p_s ) {
        return p_s.replaceAll("'", "''");
    }
    public String unescapetics( final String p_s ) {
        return p_s.replaceAll("''", "'");
    }
    public int compare( final String p_s1,  final String p_s2 ) {
        return p_s1.compareTo( p_s2 );
    }
    public String openblockcomment() {
        // Insert your implementation here
        return "";
    }
    public String closeblockcomment() {
        // Insert your implementation here
        return "";
    }

}
