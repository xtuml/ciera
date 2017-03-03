package library;

import org.xtuml.bp.core.ComponentInstance_c;
import java.util.ArrayList;

public interface ISQLToProvider {
    public static int LEN_VALUES = 32;
    public void insert(ComponentInstance_c senderReceiver, String table, ArrayList<String> values);
}
