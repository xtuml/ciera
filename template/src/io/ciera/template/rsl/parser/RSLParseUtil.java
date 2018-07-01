package io.ciera.template.rsl.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import io.ciera.template.RSL;

public class RSLParseUtil {
    
    public static void parse( String filename, RSL population ) {
        File template_dir = new File( filename );
        processFile( template_dir.getAbsoluteFile(), "", "", population );
    }
    
    private static void processFile( File f, String basePath, String relativePath, RSL population ) {
        if ( null != f && f.exists() && null != basePath && null != relativePath ) {
            System.err.printf( "--   Processing file: %s\n", f.getName() );
            if ( f.isDirectory() ) {
                if ( "".equals( basePath ) ) basePath = f.getAbsolutePath();
                else relativePath = "".equals( relativePath ) ? f.getName() : relativePath + File.separator + f.getName();
                for ( File contained_file : f.listFiles() ) processFile( contained_file, basePath, relativePath, population );
            }
            else {
                try {
                    RSLLexer lexer = new RSLLexer( CharStreams.fromStream( new FileInputStream( basePath + File.separator + relativePath + File.separator + f.getName() ) ) );
                    /* lexer debug
                    for ( Token token = lexer.nextToken(); token.getType() != Token.EOF; token = lexer.nextToken() ) {
                        System.err.println( lexer.getVocabulary().getSymbolicName(token.getType()));
                    }
                    */
                    CommonTokenStream tokens = new CommonTokenStream( lexer );
                    RSLParser parser = new RSLParser( tokens );
                    RSLParser.BodyContext ctx = parser.body();
                    ParseTreeWalker walker = new ParseTreeWalker();
                    XtumlRSLListener listener = new XtumlRSLListener( relativePath + File.separator + f.getName(), population );
                    walker.walk( listener, ctx );
                } catch ( IOException e ) { /* do nothing */ }
            }
        }
    }

}
