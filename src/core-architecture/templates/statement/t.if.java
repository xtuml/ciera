if (${cond_expr}) ${then_block}\
.if (else_block != "")
  .if (not exclude_comments and else_if_actions != "")

/* ${else_if_actions} */
  .else
 \
  .end if
else ${else_block}\
.end if
.if (not self.is_else_if)

.end if
