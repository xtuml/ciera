package library;

import org.xtuml.bp.core.ComponentInstance_c;
import java.util.ArrayList;
import lib.LOG;

public class SQLparser implements IparseToProvider, ISQLFromProvider {

    private ISQLToProvider SQL;

    public SQLparser( ISQLToProvider p_SQL, IparseFromProvider p_parse ) {
        LOG.LogInfo("SQLparser constructor");
        SQL = p_SQL;
    }

    @Override
    public void parse(ComponentInstance_c senderReceiver) {
        LOG.LogInfo("testing");
        ArrayList<String> values = new ArrayList<String>();
        SQL.insert( null, "levi", values );
    }

}
