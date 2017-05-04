package lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import sqlinsert.SqlInsertHandler;
import sqlinsert.SqlInsertParse;

public class POPULATION implements SqlInsertHandler {
    
    private static boolean populating = false;
    
    public static void populate( String file, String systemName ) {
        LOG.LogInfo("Populating... file: " + file + ", system: " + systemName);
        if ( !populating ) {
            populating = true;
            POPULATION pop = new POPULATION();
            pop.doPopulate(file, systemName);
        }
    }
    
    private void doPopulate( String file, String systemName ) {
        if ( file == null || file.equals("")|| systemName == null || systemName.equals("") ) return;

        LOG.LogInfo("Populating... file: " + file + ", system: " + systemName);
        
        // initialize
        Populator.initialize( systemName );
        
        LOG.LogInfo("initialized");
        
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

        LOG.LogInfo("done inserting");
        
        // relate instances
        Populator.relate();
    }

    @Override
    public void insert(String table, ArrayList<String> values) {
        LOG.LogInfo("insert");
        Populator.insert( table, values );
    }
    
}
