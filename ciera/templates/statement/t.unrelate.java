.if ( "" != oal )
${indent}// ${oal}
.end if
${indent}context().unrelate_${self.rel_name}( ${form_expr}, ${part_expr} );
