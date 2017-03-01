package library;

import java.util.ArrayList;
import lib.LOG;
import org.xtuml.bp.core.ComponentInstance_c;

public class SQLInsertParser implements IparseToProvider, ISQLFromProvider {

    private ISQLToProvider SQL;
    private IparseFromProvider parse;
    
    private boolean parsing = false;

    public SQLInsertParser( ISQLToProvider p_SQL, IparseFromProvider p_parse ) {
        SQL = p_SQL;
        parse = p_parse;
    }

    @Override
    public void parse(ComponentInstance_c senderReceiver) {
    	if ( parsing ) LOG.LogFailure("SQLInserParser: Parse already in progress.");
    	else {
    		LOG.LogInfo("SQLInserParser: Initiating SQL parse...");
    		parse();
    	}
    }
    
    private void parse() {
        ArrayList<String> values = new ArrayList<String>();
        values.add("courtney");
        values.add("is");
        values.add("hot");
        SQL.insert( null, "levi", values );
        done();
    }
    
    private void done() {
    	if ( parse != null ) parse.done(null);
    	else LOG.LogFailure("SQLInserParser: Null port 'parse'.");
    }

}
