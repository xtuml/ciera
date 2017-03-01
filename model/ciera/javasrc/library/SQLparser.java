package library;

import org.xtuml.bp.core.ComponentInstance_c;
import java.util.ArrayList;
import lib.LOG;

public class SQLparser implements IparseToProvider, ISQLFromProvider {

    private ISQLToProvider SQL;
    private IparseFromProvider parse;

    public SQLparser( ISQLToProvider p_SQL, IparseFromProvider p_parse ) {
        SQL = p_SQL;
        parse = p_parse;
    }

    @Override
    public void parse(ComponentInstance_c senderReceiver) {
        LOG.LogInfo("Received parse signal");
        ArrayList<String> values = new ArrayList<String>();
        SQL.insert( null, "levi", values );
        parse.done(null);
    }

}
