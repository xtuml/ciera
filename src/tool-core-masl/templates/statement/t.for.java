.if ( "" != oal )
${indent}// ${oal}
.end if
${prefix}${indent}for ( Iterator<${iterator_type}> _${self.iterator_name}_iter = ${iterable_expr}.elements().iterator(); _${self.iterator_name}_iter.hasNext(); ) ${control_block}\
