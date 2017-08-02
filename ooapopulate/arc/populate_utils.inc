.function get_root_package_name
    .param inst_ref o_obj
    .select one pe related by o_obj->PE_PE[R8001]
    .assign attr_name = ""
    .while ( not_empty pe )
        .select one package related by pe->EP_PKG[R8000]
        .select one comp related by pe->C_C[R8003]
        .if ( not_empty package )
            .assign attr_name = package.Name
            .select one pe related by package->PE_PE[R8001]
        .elif ( not_empty comp )
            .select one pe related by comp->PE_PE[R8001]
        .else
            .select any pe from instances of PE_PE where ( false )
        .end if
    .end while
.end function

.function get_attribute
    .param inst_ref o_attr
    .param inst_ref s_dt
    .param string o_attr_val
    .select one s_edt related by s_dt->S_EDT[R17] 
    .if ( not_empty s_edt )
        .// use integer for enums
        .select any s_dt from instances of S_DT where ( selected.Name == "integer" )
    .end if
    .select one s_udt related by s_dt->S_EDT[R17] 
    .if ( not_empty s_udt )
        .// use core type for UDTs
        .select one s_dt related by s_udt->S_DT[R18] 
    .end if
    .if ( "boolean" == s_dt.Name )
${o_attr_val}.equals( "false" ) ? false : true\
    .elif ( "integer" == s_dt.Name )
Integer.parseInt(${o_attr_val})\
    .elif ( "real" == s_dt.Name )
Float.parseFloat(${o_attr_val})\
    .elif ( "long" == s_dt.Name )
Long.parseLong(${o_attr_val})\
    .elif ( "string" == s_dt.Name )
removeTics(${o_attr_val})\
    .elif ( "unique_id" == s_dt.Name )
createUUIDFromString(${o_attr_val})\
    .else
        .print "Unsupported attribute type: ${s_dt.Name}"
null\
    .end if
.end function

.function get_first_attribute
	.param inst_ref o_obj
    .select any attr_first_attribute related by o_obj->O_ATTR[R102]
    .select one prior_attribute related by attr_first_attribute->O_ATTR[R103.'precedes']
    .while ( not_empty prior_attribute )
    	.assign attr_first_attribute = prior_attribute
    	.select one prior_attribute related by attr_first_attribute->O_ATTR[R103.'precedes']
    .end while
.end function
