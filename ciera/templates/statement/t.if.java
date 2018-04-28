${indent}// ${if_oal}
${indent}if ( ${cond_expr} ) ${then_block}\
.if ( "" != else_block )
  .if ( "" != else_oal )
${indent}// ${else_oal}
  .end if
${indent}else ${else_block}\
.end if
