if (${cond_expr}) ${then_block}\
.if (else_block != "")
  .if (include_comments and else_if_actions != "")
/* ${else_if_actions} */
  .end if
else ${else_block}\
.end if
