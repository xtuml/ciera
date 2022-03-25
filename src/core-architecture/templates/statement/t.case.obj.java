.if (self.actions != "" and not exclude_comments)
/* ${self.actions} */
.end if
.if (expr != "")
  .if (empty prev_case)
if (\
  .elif (not prev_case.duplicate)
else if (\
  .else
 || \
  .end if
  .if (is_primitive_type)
_switch_expr == ${expr}\
  .else
_switch_expr.equals(${expr})\
  .end if
  .if (not self.duplicate)
) ${then_block}
  .end if
.else
else ${then_block}
.end if
