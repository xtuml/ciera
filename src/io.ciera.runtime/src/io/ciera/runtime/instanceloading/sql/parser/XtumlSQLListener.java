package io.ciera.runtime.instanceloading.sql.parser;

import java.util.ArrayList;
import java.util.List;

import io.ciera.runtime.instanceloading.IPopulationLoader;
import io.ciera.runtime.instanceloading.sql.parser.SQLParser.Insert_statementContext;
import io.ciera.runtime.instanceloading.sql.parser.SQLParser.Sql_fileContext;
import io.ciera.runtime.instanceloading.sql.parser.SQLParser.Table_nameContext;
import io.ciera.runtime.instanceloading.sql.parser.SQLParser.ValueContext;
import io.ciera.runtime.summit.exceptions.XtumlException;

public class XtumlSQLListener extends SQLBaseListener {

    private IPopulationLoader loader;
    private String tableName;
    private List<Object> values;

    public XtumlSQLListener(IPopulationLoader loader) {
        this.loader = loader;
        tableName = null;
        values = null;
    }

    @Override
    public void exitSql_file(Sql_fileContext ctx) {
        try {
            loader.finish();
        } catch (XtumlException e) {
            e.printStackTrace();
            System.exit(1);
            // TODO
        }
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
            e.printStackTrace();
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
            System.err.println("This is bad"); // TODO
        }
    }

}
