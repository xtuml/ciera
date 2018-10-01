.if ( "" != oal )
${indent}// ${oal}
.end if
${prefix}${indent}context().relate_${self.rel_name}( ${form_expr}, ${part_expr} );
