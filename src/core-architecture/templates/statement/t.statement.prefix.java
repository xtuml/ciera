.if (include_comments and statement.actions != "" and (empty if_smt or not if_smt.is_else_if))
/* ${statement.actions} */
.end if
${statement.prefix}\
