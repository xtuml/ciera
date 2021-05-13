package io.ciera.runtime.instanceloading.sql;

import java.util.ArrayList;
import java.util.List;

import io.ciera.runtime.instanceloading.sql.parser.SQLBaseListener;
import io.ciera.runtime.instanceloading.sql.parser.SQLParser.Insert_statementContext;
import io.ciera.runtime.instanceloading.sql.parser.SQLParser.Sql_fileContext;
import io.ciera.runtime.instanceloading.sql.parser.SQLParser.Table_nameContext;
import io.ciera.runtime.instanceloading.sql.parser.SQLParser.ValueContext;
import io.ciera.runtime.instanceloading.sql.parser.SQLParser.Link_statementContext;
import io.ciera.runtime.instanceloading.sql.parser.SQLParser.Rel_numberContext;
import io.ciera.runtime.instanceloading.sql.parser.SQLParser.IdContext;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.exceptions.XtumlException;

public class SqlListener extends SQLBaseListener {

    private IRunContext runContext;
    private ISqlLoader loader;

    private String tableName;
    private List<Object> values;
    private List<Object> instids;
    private Integer relNumber;
    
    public SqlListener(ISqlLoader loader, IRunContext runContext) {
        this.loader = loader;
        this.runContext = runContext;
        tableName = null;
        values = null;
    }

    @Override
    public void exitSql_file(Sql_fileContext ctx) {
/*        try {
            loader.batchRelate();
        } catch (XtumlException e) {
            runContext.getLog().error(e);
            System.exit(1);
            // TODO
        } */
    }

    @Override
    public void enterInsert_statement(Insert_statementContext ctx) {
        values = new ArrayList<>();
    }

    @Override
    public void exitInsert_statement(Insert_statementContext ctx) {
        try {
            loader.insert(tableName, values);
        } catch (XtumlException e) {
            runContext.getLog().error(e);
            System.exit(1);
            // TODO
        }
    }
 
    @Override
    public void enterLink_statement(Link_statementContext ctx) {
        instids = new ArrayList<>();
    }

    @Override
    public void exitLink_statement(Link_statementContext ctx) {
        try {
        	System.out.printf( " link %d\n", relNumber );
            loader.link(relNumber, instids);
        } catch (XtumlException e) {
            runContext.getLog().error(e);
            System.exit(1);
            // TODO
        }
    }

    
    @Override
    public void exitTable_name(Table_nameContext ctx) {
        tableName = ctx.ID().getText();
    }

    @Override
    public void exitValue(ValueContext ctx) {
        if (null != ctx.STRING()) {
            String text = ctx.STRING().getText();
            values.add(text.substring(1, text.length() - 1).replaceAll("''", "'"));
        } else if (null != ctx.REAL()) {
            values.add(Double.parseDouble(ctx.REAL().getText()));
        } else if (null != ctx.INTEGER()) {
        	long longValue = Long.parseLong(ctx.INTEGER().getText());
        	try {
        		values.add(Math.toIntExact(longValue));
        	} catch (ArithmeticException e) {
        		values.add(longValue);
        	}
        } else {
            runContext.getLog().error("This is bad"); // TODO
        }
    }

    @Override
    public void exitRel_number(Rel_numberContext ctx) {
        if (null != ctx.INTEGER()) {
        	long longValue = Long.parseLong(ctx.INTEGER().getText());
        	try {
                relNumber = Math.toIntExact(longValue);
        	} catch (ArithmeticException e) {
                runContext.getLog().error(e);
                System.exit(1);
        	}
        } else {
            runContext.getLog().error("This is bad"); // TODO
        }
    }

    @Override
    public void exitId(IdContext ctx) {
        if (null != ctx.STRING()) {
            String text = ctx.STRING().getText();
            instids.add(text.substring(1, text.length() - 1).replaceAll("''", "'"));
        }
    }
}
