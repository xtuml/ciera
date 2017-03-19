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
