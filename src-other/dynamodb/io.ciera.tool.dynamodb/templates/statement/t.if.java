.if ( "" != if_oal )
${indent}// ${if_oal}
.end if
.if ( not self.is_else_if )
${indent}\
.end if
if ( ${cond_expr} ) ${then_block}\
.if ( "" != else_block )
  .if ( "" != else_oal )
${indent}// ${else_oal}
  .end if
${indent}else ${else_block}\
.end if
