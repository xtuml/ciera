.include "../arc/populate_utils.inc"
.//
// Populate OOA of OOA
package lib;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.xtuml.bp.core.*;
import org.xtuml.bp.core.common.*;

public class Populator {

    private static ModelRoot modelRoot;
    private static List<NonRootModelElement> loadedInstances;
    
    public static void initialize( String systemName ) {
        Ooaofooa[] ooas = Ooaofooa.getInstancesUnderSystem( systemName );
        if ( ooas == null || ooas.length != 1 || ooas[0] == null ) {
            LOG.LogFailure("Error getting model root under '" + systemName + "'");
        }
        else modelRoot = ooas[0];
        loadedInstances = new ArrayList<NonRootModelElement>();
    }

    public static void insert( String table, ArrayList<String> values ) {
        create( table, values );
    }

    public static void relate() {
        for ( NonRootModelElement element : loadedInstances ) {
            element.batchRelate( modelRoot, false, true );
        }
    }

.select many o_objs from instances of O_OBJ;
.for each o_obj in o_objs
    .invoke root_pkg = get_root_package_name( o_obj )
    .if ( ("$l{o_obj.Descrip:Persistent}" != "false") and ( root_pkg.name == "ooaofooa") )
    private static void create${o_obj.Key_Lett}( ArrayList<String> values ) {
        // create instance of ${o_obj.Key_Lett}
        $Cr{o_obj.Name}_c inst = null;
        inst = new $Cr{o_obj.Name}_c( modelRoot\
        .assign index = 0
        .invoke first_attr = get_first_attribute( o_obj )
		.assign o_attr = first_attr.first_attribute
        .while ( not_empty o_attr )
            .assign orig_o_attr = o_attr
            .select one s_dt related by o_attr->S_DT[R114]
            .if ( ("$l{o_attr.Descrip:Persistent}" != "false") and (s_dt.Name != "state<State_Model>") )
                .select one o_rattr related by o_attr->O_RATTR[R106]
                .if ( not_empty o_rattr )
                    .// if it is a referential, navigate back to the base attribute
                    .select one o_attr related by o_rattr->O_BATTR[R113]->O_ATTR[R106]
                .end if
                .select one s_dt related by o_attr->S_DT[R114]
                .invoke attr_accessor = get_attribute( o_attr, s_dt, "values.get(${index})" )
, ${attr_accessor.body} \
                .assign index = index + 1
            .end if
            .select one o_attr related by orig_o_attr->O_ATTR[R103.'succeeds']
        .end while
);
        loadedInstances.add( inst );
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
    
    private static UUID createUUIDFromString( String uuid ) {
        if ( null == uuid || uuid.isEmpty() ) return null;
        Long longval = 0L;
        try {       
            longval = Long.parseLong(uuid);
        } catch (NumberFormatException nfe) {
            LOG.LogFailure("Could not parse id '" + uuid + "'");
        }
        return new UUID(0, longval);
    }

    private static String removeTics( String str ) {
		if ( null == str || str.isEmpty() ) return "";
		String out = str;
		if (out.startsWith("'")) {      
			out = out.substring(1);         
		}   
        if (out.endsWith("'")) {        
            out = out.substring(0, out.length() - 1);
        }   
        out = out.replaceAll("''", "'");
        return out;
    }
    
}
.//
.emit to file "../src/lib/Populator.java"
