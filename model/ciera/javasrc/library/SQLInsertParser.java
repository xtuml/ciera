package library;

import java.io.*;
import java.util.ArrayList;

import lib.LOG;
import org.xtuml.bp.core.ComponentInstance_c;
import sqlinsert.SqlInsertHandler;
import sqlinsert.SqlInsertParse;

public class SQLInsertParser implements IparseToProvider, ISQLFromProvider, SqlInsertHandler {

    private ISQLToProvider SQL;
    private IparseFromProvider parse;
    
    private boolean parsing = false;

    public SQLInsertParser( ISQLToProvider p_SQL, IparseFromProvider p_parse ) {
        SQL = p_SQL;
        parse = p_parse;
    }

    @Override
    public void parse(ComponentInstance_c senderReceiver, String filepath) {
        if ( parsing ) LOG.LogFailure("SQLInsertParser: Parse already in progress.");
        else {
            LOG.LogInfo("SQLInsertParser: Initiating SQL parse...");
            parse(filepath);
        }
    }
    
    @Override
    public void insert(String table, ArrayList<String> values) {
        if ( table != null && !table.equals("") && values != null && values.size() == ISQLToProvider.LEN_VALUES ) {
            SQL.insert( null, table, values );
        }
        else {
            LOG.LogFailure("SQLInsertParser: Malformed insert statement.");
        }
    }
    
    private void parse(String filepath) {
        if ( filepath == null || filepath.equals("") ) return;
        SqlInsertParse sqlinsert = new SqlInsertParse();
        File infile = new File(filepath);
        InputStream in = null;
        try {
            in = new FileInputStream( infile );
            parsing = true;
            sqlinsert.parse( in, this );
        }
        catch ( FileNotFoundException e ) {
            LOG.LogFailure(e.toString());
        }
        done();
    }
    
    private void done() {
        if ( parsing ) {
            if ( parse != null ) {
                parsing = false;
                LOG.LogInfo("SQLInsertParser: Finished parse.");
                parse.done(null);
            }
            else LOG.LogFailure("SQLInsertParser: Null port 'parse'.");
        }
        else LOG.LogFailure("SQLInsertParser: Not parsing.");
    }

}
