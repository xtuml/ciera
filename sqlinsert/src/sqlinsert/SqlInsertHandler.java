package sqlinsert;

import java.util.List;

public interface SqlInsertHandler {
    public static int LEN_VALUES = 32;
    public void insert(String table, List<String> values);
}
