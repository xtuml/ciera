package io.ciera.sql.util.impl;

import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import io.ciera.cairn.util.Utility;
import io.ciera.sql.parser.SQLLexer;
import io.ciera.sql.parser.SQLParser;
import io.ciera.sql.parser.SQLParser.Sql_fileContext;
import io.ciera.sql.parser.XtumlSQLListener;
import io.ciera.sql.util.SQL;
import io.ciera.summit.components.IComponent;
import io.ciera.summit.exceptions.InstancePopulationException;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.XtumlString;

public class SQLImpl<C extends IComponent<C>> extends Utility<C> implements SQL {

    public SQLImpl( C context ) {
        super( context );
    }

    @Override
    public void load() throws XtumlException {
        // TODO
    }

    @Override
    public void load_file( XtumlString file ) throws XtumlException {
        SQLLexer lexer;
        try {
            lexer = new SQLLexer( CharStreams.fromFileName( file.toString() ) );
        } catch ( IOException e ) {
            throw new InstancePopulationException( "Could not read file." );
        }
        CommonTokenStream tokens = new CommonTokenStream( lexer );
        SQLParser parser = new SQLParser( tokens );
        Sql_fileContext ctx = parser.sql_file();
        ParseTreeWalker walker = new ParseTreeWalker();
        XtumlSQLListener listener = new XtumlSQLListener( null ); // TODO
        walker.walk( listener, ctx );
    }

    @Override
    public void serialize() throws XtumlException {
        // TODO
    }

    @Override
    public void serialize_file( XtumlString file ) throws XtumlException {
        // TODO
    }

}
