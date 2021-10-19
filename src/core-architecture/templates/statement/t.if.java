.if ( "" != if_actions )
${indent}/* ${if_actions} */
.end if
.if ( not self.is_else_if )
${indent}\
.end if
if ( ${cond_expr} ) ${then_block}\
.if ( "" != else_block )
  .if ( "" != else_actions )
${indent}/* ${else_actions} */
  .end if
${indent}else ${else_block}\
.end if
