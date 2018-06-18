package io.ciera.template.rsl.parser.test;

import java.io.FileInputStream;
import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import io.ciera.template.rsl.parser.RSLLexer;
import io.ciera.template.rsl.parser.RSLParser;
import io.ciera.template.rsl.parser.XtumlRSLListener;


public class TestRSL {

    private static final String FILENAME = "/Users/levi/git/ciera/ciera/templates/relationship/t.referentialattributeinitialization.java";
    
    public static void main( String[] args ) {
        try {
            RSLLexer lexer;
          /*
            lexer = new RSLLexer( CharStreams.fromStream( new FileInputStream( FILENAME ) ) );
            Token next;
            while ( null != (next=lexer.nextToken()) && next.getType() != Token.EOF) {
                System.out.printf( "%10s: '%s'\n", RSLLexer.VOCABULARY.getSymbolicName( next.getType() ), next.getText() );
            }
            */
            lexer = new RSLLexer( CharStreams.fromStream( new FileInputStream( FILENAME ) ) );
            CommonTokenStream tokens = new CommonTokenStream( lexer );
            RSLParser parser = new RSLParser( tokens );
            RSLParser.BodyContext ctx = parser.body();
            ParseTreeWalker walker = new ParseTreeWalker();
            XtumlRSLListener listener = new XtumlRSLListener( FILENAME );
            walker.walk( listener, ctx );
        } catch ( IOException e ) { /* do nothing */ }
    }

}
