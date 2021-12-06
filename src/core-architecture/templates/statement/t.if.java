.if (if_actions != "")
/* ${if_actions} */
.end if
if (${cond_expr}) ${then_block}\
.if (else_block != "")
  .if (else_actions != "")
/* ${else_actions} */
  .end if
else ${else_block}\
.end if
