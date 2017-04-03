package lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import sqlinsert.SqlInsertHandler;
import sqlinsert.SqlInsertParse;

public class POPULATE implements SqlInsertHandler {
    
    private static boolean populating = false;
    
    public static void populate( String file, String systemName ) {
        if ( !populating ) {
            populating = true;
            POPULATE pop = new POPULATE();
            pop.doPopulate(file, systemName);
        }
    }
    
    private void doPopulate( String file, String systemName ) {
        if ( file == null || file.equals("")|| systemName == null || systemName.equals("") ) return;
        
        // initialize
        Populator.initialize( systemName );
        
        // parse file
        SqlInsertParse sqlinsert = new SqlInsertParse();
        File infile = new File(file);
        InputStream in = null;
        try {
            in = new FileInputStream( infile );
            sqlinsert.parse( in, this );
        }
        catch ( FileNotFoundException e ) {
            LOG.LogFailure(e.toString());
        }
        
        // relate instances
        Populator.relate();
    }

    @Override
    public void insert(String table, ArrayList<String> values) {
        Populator.insert( table, values );
    }
    
}
