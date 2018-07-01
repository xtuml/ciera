package io.ciera.sql.parser;

import java.io.FileInputStream;
import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

public class SQLParseUtil {
    
    private static final String IN_FILE = "gen/code_generation/a.xtuml";

    public static void main( String[] args ) {
        try {
            SQLLexer lexer = new SQLLexer( CharStreams.fromStream( new FileInputStream(IN_FILE) ) );
            int i = 0;
            for ( Token token = lexer.nextToken(); token.getType() != Token.EOF; token = lexer.nextToken() ) {
                System.err.println( lexer.getVocabulary().getSymbolicName(token.getType()));
                if ( i++ == i ) break;
            }
        } catch ( IOException e ) { /* do nothing */ }
    }
    
}