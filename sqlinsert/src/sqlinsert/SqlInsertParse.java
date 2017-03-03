package sqlinsert;

import java.io.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import sqlinsert.SqlInsertParser.Sql_fileContext;

public class SqlInsertParse {

    // parse a directory of MASL files
    public void parse() {
        File infile = new File("/Users/levistarrett/git/xtuml/ciera/model/ciera/test_data/ooaofgraphics.sql");
        InputStream in = null;
        try {
        	in = new LowerCaseInputStream( new FileInputStream( infile ) );
        }
        catch ( FileNotFoundException e ) {
        	System.err.println(e);
        }
        parse( in );
    }

    // parse a MASL file
    public void parse( InputStream in ) {
        // check args and set current file
        if ( in == null )
            return;

        SqlInsertLexer          lex;
        CommonTokenStream       tokens;
        SqlInsertParser         parser;
        
        try {
            lex = new SqlInsertLexer( new ANTLRInputStream( in ) );
        } catch ( IOException e ) {
            System.err.println( e );
            return;
        }

        tokens = new CommonTokenStream( lex );
        parser = new SqlInsertParser( tokens );

        Sql_fileContext ctx = parser.sql_file();
        
        ParseTreeWalker walker = new ParseTreeWalker();
        SqlInsertPopulator listener = new SqlInsertPopulator();
        walker.walk(listener, ctx);

    }

    // main method
    public static void main(String args[]) throws Exception {

        SqlInsertParse sqlinsert = new SqlInsertParse();
        sqlinsert.parse();

    }
}

