package io.ciera.template.util.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.ciera.cairn.util.Utility;
import io.ciera.summit.components.IComponent;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.IXtumlType;
import io.ciera.template.util.ITemplateRegistry;
import io.ciera.template.util.TEMP;

public class TEMPImpl<C extends IComponent<C>> extends Utility<C> implements TEMP {
    
    private Stack<String> buffers;
    private ITemplateRegistry registry;

    public TEMPImpl( C context ) {
        super( context );
        buffers = new Stack<>();
        try {
            Class<?> componentClass = context.getClass();
            Class<?> registryClass = Class.forName( componentClass.getName() + "TemplateRegistry" );
            Constructor<?> registryConstructor = registryClass.getConstructor( componentClass );
            registry = (ITemplateRegistry)registryConstructor.newInstance( context );
        } catch ( Exception e ) {
            registry = null;
        }
    }

    @Override
    public void pushBuffer() {
        buffers.push( "" );
    }

    @Override
    public void popBuffer() {
        buffers.pop();
    }

    @Override
    public void append( String s ) throws XtumlException {
        buffers.push( buffers.pop() + s );
    }

    @Override
    public String body() throws XtumlException {
        return buffers.peek();
    }

    @Override
    public void clear() throws XtumlException {
        buffers.pop();
        buffers.push( "" );
    }

    @Override
    public void emit( String file ) throws XtumlException {
        if ( null != file ) {
            try {
                File outputFile = new File( file );
                boolean preExists = outputFile.exists();
                PrintStream out = new PrintStream( outputFile );
                out.print( buffers.peek() );
                out.flush();
                out.close();
                if ( preExists ) System.out.printf( "File '%s' REPLACED.\n", file );
                else System.out.printf( "File '%s' CREATED.\n", file );
            } catch ( IOException e ) {
                throw new XtumlException( "Could not open output file." );
            }
        }
    }

    @Override
    public void include( String file ) throws XtumlException {
        if ( null != file && null != registry ) registry.getTemplate( file ).evaluate();
    }

    /** 
     * u   Upper - make all characters upper case
     * c   Capitalize - make the first character of each word capitalized and
     *     all other characters of a word lowercase
     * l   Lower - make all characters lowercase
     * _   Underscore - change all whitespace characters to underscore
     *     characters
     * r   Remove - remove all whitespace.
     * o   cOrba - make the first word all lowercase, make the first
     *     character of each following word capitalized and all other characters
     *     of the words lowercase. Characters other than a-Z a-z 0-9 are ignored.
     * t   no-op
     */
    @Override
    public String sub( String format, boolean b ) throws XtumlException {
        return sub( format, Boolean.toString(b) );
    }

    @Override
    public String sub( String format, int i ) throws XtumlException {
        return sub( format, Integer.toString(i) );
    }

    @Override
    public String sub( String format, double d ) throws XtumlException {
        return sub( format, Double.toString(d) );
    }

    @Override
    public String sub( String format, IXtumlType<?> o ) throws XtumlException {
        return sub( format, o );
    }

    @Override
    public String sub( String format, String s ) throws XtumlException {
        if ( null != format ) {
            for ( int i = 0; i < format.length(); i++ ) {
                switch ( format.charAt( i ) ) {
                case 'U':
                case 'u':
                    s = upper( s );
                    break;
                case 'C':
                case 'c':
                    s = capitalize( s );
                    break;
                case 'L':
                case 'l':
                    s = lower( s );
                    break;
                case '_':
                    s = underscore( s );
                    break;
                case 'R':
                case 'r':
                    s = remove( s );
                    break;
                case 'O':
                case 'o':
                    s = cobra( s );
                    break;
                case 'T':
                case 't':
                    break;
                default:
                    throw new XtumlException( "Unrecognized format character '" + format.charAt( i ) + "'" );
                }
            }
        }
        return s;
    }
    
    private String upper( String s ) {
        if ( null == s ) return s;
        else return s.toUpperCase();
    }

    private String capitalize( String s ) {
        if ( null == s ) return s;
        else {
            Matcher m = Pattern.compile( "[^\\s]+\\b" ).matcher( s );
            String baseString = s;
            while ( m.matches() ) {
                baseString = baseString.substring( 0, m.start() ) +
                             baseString.substring( m.start(), m.start()+1 ).toUpperCase() +
                             baseString.substring( m.start()+1, m.end() ).toLowerCase() +
                             baseString.substring( m.end() );
            }
            s = baseString;
        }
        return s;
    }

    private String lower( String s ) {
        if ( null == s ) return s;
        else return s.toLowerCase();
    }
        
    private String underscore( String s ) {
        if ( null == s ) return s;
        else return s.replaceAll( "\\s", "_" );
    }
        
    private String remove( String s ) {
        if ( null == s ) return s;
        else return s.replaceAll( "\\s+", "" );
    }
        
    private String cobra( String s ) {
        if ( null == s ) return s;
        else {
            s = capitalize( s );
            return s.substring( 0, 1 ).toLowerCase() + s.substring( 1 );
        }
    }

}
