package io.ciera.template.util.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.Stack;

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
                if ( preExists ) System.out.printf( "File '%s' REPLACED.\n", file.toString() );
                else System.out.printf( "File '%s' CREATED.\n", file.toString() );
            } catch ( IOException e ) {
                throw new XtumlException( "Could not open output file." );
            }
        }
    }

    @Override
    public void include( XtumlString file ) throws XtumlException {
        if ( null != file && null != registry ) registry.getTemplate( file.toString() ).evaluate();
    }

    @Override
    public XtumlString sub( XtumlString format, XtumlString s ) throws XtumlException {
        return s; // TODO
    }

}
