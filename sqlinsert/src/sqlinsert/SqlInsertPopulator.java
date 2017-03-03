package sqlinsert;

public class SqlInsertPopulator extends SqlInsertBaseListener {
	
	@Override
	public void enterInsert_statement(SqlInsertParser.Insert_statementContext ctx) {
		System.out.println(ctx.getText());
	}

}
