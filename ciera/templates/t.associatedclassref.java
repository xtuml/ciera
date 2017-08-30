.if ( declaration )
    ${target_type_name} ${ref_name};
.elif ( initialize )
  .if ( is_many )
        ${ref_name} = new ${target_type_name}();
  .else
        ${ref_name} = ${target_type_name}.empty${target_type_name};
  .end if
.end if
