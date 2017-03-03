package sqlinsert;

import java.util.ArrayList;
import org.antlr.v4.runtime.Token;
import sqlinsert.SqlInsertParser.*;

public class SqlInsertPopulator extends SqlInsertBaseListener {
	
	private String table = null;
	private ArrayList<String> values = null;
	private SqlInsertHandler handler;
	
	public SqlInsertPopulator( SqlInsertHandler insertHandler ) {
		handler = insertHandler;
	}
	
	private void insert() {
		if ( handler != null ) handler.insert( table, values );
		table = null;
		values = null;
	}
	
	@Override
	public void exitInsert_statement(Insert_statementContext ctx) {
		insert();
	}

	@Override
	public void exitTable_name(Table_nameContext ctx) {
		// set the table name
		table = ctx.getText();
		// initialize the values
		values = new ArrayList<String>(32);
	}
	
	@Override
	public void exitData_value(Data_valueContext ctx) {
		Token tok = ctx.getStart();
		switch ( tok.getType() ) {
			case SqlInsertLexer.STRING:
				values.add(tok.getText().substring(1,tok.getText().length()-1));
				break;
			case SqlInsertLexer.NUMBER:
				values.add(tok.getText());
				break;
		}
	}

}
