package sqlinsert;

import java.io.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import lib.LOG;
import sqlinsert.SqlInsertParser.Sql_fileContext;

public class SqlInsertParse {

    // parse a test file
    public void test_parse() {
        File infile = new File("/Users/levi/git/xtuml/ciera/model/ciera/test_data/ooaofgraphics.sql");
        InputStream in = null;
        try {
            in = new FileInputStream( infile );
        }
        catch ( FileNotFoundException e ) {
            System.err.println(e);
        }
        parse( in, null );
    }

    // parse
    public void parse( InputStream in, SqlInsertHandler handler ) {
        // check args and set current file
        if ( in == null )
            return;
        
        SqlInsertLexer          lex;
        CommonTokenStream       tokens;
        SqlInsertParser         parser;
        
        try {
            LOG.LogInfo("SQLInsertParse: Lexing...");
            lex = new SqlInsertLexer( new ANTLRInputStream( new LowerCaseInputStream( in ) ) );
        } catch ( IOException e ) {
            System.err.println( e );
            return;
        }

        tokens = new CommonTokenStream( lex );
        parser = new SqlInsertParser( tokens );

        LOG.LogInfo("SQLInsertParse: Parsing...");
        Sql_fileContext ctx = parser.sql_file();
        
        ParseTreeWalker walker = new ParseTreeWalker();
        SqlInsertPopulator listener = new SqlInsertPopulator(handler);

        LOG.LogInfo("SQLInsertParse: Walking...");
        walker.walk(listener, ctx);

    }

    // main method
    public static void main(String args[]) throws Exception {
        SqlInsertParse sqlinsert = new SqlInsertParse();
        sqlinsert.test_parse();
    }
}

