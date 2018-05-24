.if ( "" != oal )
${indent}// ${oal}
.end if
${indent}population().relate_${self.rel_name}( ${form_expr}, ${part_expr} );
