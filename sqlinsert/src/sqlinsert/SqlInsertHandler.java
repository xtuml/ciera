package sqlinsert;

import java.util.ArrayList;

public interface SqlInsertHandler {
    public static int LEN_VALUES = 32;
    public void insert(String table, ArrayList<String> values);
}
