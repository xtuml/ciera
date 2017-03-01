package library;

import org.xtuml.bp.core.ComponentInstance_c;
import java.util.ArrayList;

public interface ISQLToProvider {

    public void insert(ComponentInstance_c senderReceiver, String table, ArrayList<String> values);

}
