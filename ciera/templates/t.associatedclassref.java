.if ( declaration )
    ${target_type_name} ${ref_name};
.elif ( initialize )
        ${ref_name} = ${target_type_name}.empty${target_type_name};
.end if
