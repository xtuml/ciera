.include "../arc/populate_utils.inc"
.//
// Populate OOA of OOA
package lib;

import java.util.ArrayList;
import java.util.UUID;
import org.xtuml.bp.core.*;
import org.xtuml.bp.core.common.*;

public class POPULATE {

    private static ModelRoot modelRoot;

    public static void initialize( String systemName ) {
        Ooaofooa[] ooas = Ooaofooa.getInstancesUnderSystem( systemName );
        if ( ooas == null || ooas.length != 1 || ooas[0] == null ) {
            LOG.LogFailure("Error getting model root under '" + systemName + "'");
        }
        else modelRoot = ooas[0];
    }

    public static void insert( String table, ArrayList<String> values ) {
        create( table, values );
    }

.select many o_objs from instances of O_OBJ;
.for each o_obj in o_objs
    .invoke root_pkg = get_root_package_name( o_obj )
    .if ( ("$l{o_obj.Descrip:Persistent}" != "false") and ( root_pkg.name == "ooaofooa") )
    private static void create${o_obj.Key_Lett}( ArrayList<String> values ) {
        // create instance of ${o_obj.Key_Lett}
        $Cr{o_obj.Name}_c inst = null;
        inst = new $Cr{o_obj.Name}_c( modelRoot\
        .select many o_attrs related by o_obj->O_ATTR[R102]
        .for each o_attr in o_attrs
            .select one s_dt related by o_attr->S_DT[R114];
            .if ( ("$l{o_attr.Descrip:Persistent}" != "false") and (s_dt.Name != "state<State_Model>") )
            .end if
        .end for
);
    }

    .end if
.end for
.//
    private static void create( String table, ArrayList<String> values ) {
        if ( table == null || values == null ) return;
.assign test = "if"
.for each o_obj in o_objs
    .invoke root_pkg = get_root_package_name( o_obj )
    .if ( ("$l{o_obj.Descrip:Persistent}" != "false") and ( root_pkg.name == "ooaofooa") )
        ${test} ( table.toLowerCase().equals("$l{o_obj.Key_Lett}") ) {
            create${o_obj.Key_Lett}( values );
        }
        .assign test = "else if"
    .end if
.end for
        else {
            LOG.LogFailure("Could not populate table: '" + table + "'");
        }
    }
    
    private UUID createUUIDFromString( String uuid ) {
        if ( null == uuid || uuid.isEmpty() ) return null;
        Long longval = 0L;
        try {       
            longval = Long.parseLong(uuid);
        } catch (NumberFormatException nfe) {
            LOG.LogFailure("Could not parse id '" + uuid + "'");
        }
        return new UUID(0, longval);
    }
    
}
.//
.emit to file "../src/lib/POPULATE.java"