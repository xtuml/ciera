package io.ciera.sql.util;

import java.util.List;

public interface IInstanceLoader {
    
    public void insert( String tableName, List<Object> values );
    public void finish();

}
