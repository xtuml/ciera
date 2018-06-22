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
import io.ciera.summit.types.XtumlString;
import io.ciera.template.util.ITemplateRegistry;
import io.ciera.template.util.TEMP;

public class TEMPImpl<C extends IComponent<C>> extends Utility<C> implements TEMP {
    
    private Stack<XtumlString> buffers;
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
        buffers.push( new XtumlString( "" ) );
    }

    @Override
    public void popBuffer() {
        buffers.pop();
    }

    @Override
    public void append( XtumlString s ) throws XtumlException {
        buffers.push( buffers.pop().concat( s ) );
    }

    @Override
    public XtumlString body() throws XtumlException {
        return buffers.peek();
    }

    @Override
    public void clear() throws XtumlException {
        buffers.pop();
        buffers.push( new XtumlString( "" ) );
    }

    @Override
    public void emit( XtumlString file ) throws XtumlException {
        if ( null != file ) {
            try {
                File outputFile = new File( file.toString() );
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
    public void include( XtumlString file ) throws XtumlException {
        if ( null != file && null != registry ) registry.getTemplate( file.toString() ).evaluate();
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
     */
    @Override
    public XtumlString sub( XtumlString format, XtumlString s ) throws XtumlException {
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
                default:
                    throw new XtumlException( "Unrecognized format character '" + format.charAt( i ) + "'" );
                }
            }
        }
        return s;
    }
    
    private XtumlString upper( XtumlString s ) {
        if ( null == s || null == s.toString() ) return s;
        else return new XtumlString( s.toString().toUpperCase() );
    }

    private XtumlString capitalize( XtumlString s ) {
        if ( null == s || null == s.toString() ) return s;
        else {
            Matcher m = Pattern.compile( "[^\\s]+\\b" ).matcher( s );
            String baseString = s.toString();
            while ( m.matches() ) {
                baseString = baseString.substring( 0, m.start() ) +
                             baseString.substring( m.start(), m.start()+1 ).toUpperCase() +
                             baseString.substring( m.start()+1, m.end() ).toLowerCase() +
                             baseString.substring( m.end() );
            }
            s = new XtumlString( baseString );
        }
        return s;
    }

    private XtumlString lower( XtumlString s ) {
        if ( null == s || null == s.toString() ) return s;
        else return new XtumlString( s.toString().toLowerCase() );
    }
        
    private XtumlString underscore( XtumlString s ) {
        if ( null == s || null == s.toString() ) return s;
        else return new XtumlString( s.toString().replaceAll( "\\s", "_" ) );
    }
        
    private XtumlString remove( XtumlString s ) {
        if ( null == s || null == s.toString() ) return s;
        else return new XtumlString( s.toString().replaceAll( "\\s+", "" ) );
    }
        
    private XtumlString cobra( XtumlString s ) {
        if ( null == s || null == s.toString() ) return s;
        else {
            s = capitalize( s );
            return new XtumlString( s.toString().substring( 0, 1 ).toLowerCase() + s.toString().substring( 1 ) );
        }
    }

}
