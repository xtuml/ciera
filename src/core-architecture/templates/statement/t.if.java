if (${cond_expr}) ${then_block}\
.if (else_block != "")
  .if (else_if_actions != "")
/* ${else_if_actions} */
  .end if
else ${else_block}\
.end if
