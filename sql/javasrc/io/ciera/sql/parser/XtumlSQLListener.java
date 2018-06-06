package io.ciera.sql.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.ciera.sql.parser.SQLParser.Insert_statementContext;
import io.ciera.sql.parser.SQLParser.Sql_fileContext;
import io.ciera.sql.parser.SQLParser.Table_nameContext;
import io.ciera.sql.parser.SQLParser.ValueContext;
import io.ciera.sql.parser.SQLParser.ValuesContext;
import io.ciera.sql.util.IInstanceLoader;

public class XtumlSQLListener extends SQLBaseListener {
    
    private IInstanceLoader loader;
    private String tableName;
    private List<Object> values;
    
    public XtumlSQLListener( IInstanceLoader loader ) {
        this.loader = loader;
        tableName = null;
        values = null;
    }
    
    @Override
    public void exitSql_file( Sql_fileContext ctx ) {
        loader.finish();
    }
    
    @Override
    public void exitInsert_statement( Insert_statementContext ctx ) {
        loader.insert( tableName, values );
    }

    @Override
    public void exitTable_name( Table_nameContext ctx ) {
        tableName = ctx.ID().getText();
    }

    @Override
    public void enterValues( ValuesContext ctx ) {
        values = new ArrayList<>();
    }

    @Override
    public void exitValue( ValueContext ctx ) {
        if ( null != ctx.STRING() ) {
            String text = ctx.STRING().getText();
            values.add( text.substring( 1, text.length() - 1 ).replaceAll( "''", "'" ) );
        }
        else if ( null != ctx.REAL() ) {
            values.add( Double.parseDouble( ctx.REAL().getText() ) );
        }
        else if ( null != ctx.INTEGER() ) {
            values.add( Integer.parseInt( ctx.REAL().getText() ) );
        }
        else if ( null != ctx.UUID() ) {
            String text = ctx.STRING().getText();
            values.add( UUID.fromString( text.substring( 1, text.length() - 1 ) ) );
        }
        else {
            System.err.println( "This is bad" ); // TODO
        }
    }

}
