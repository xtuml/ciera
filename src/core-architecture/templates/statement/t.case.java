.if (self.actions != "" and not exclude_comments)
/* ${self.actions} */
.end if
.if (expr != "")
case ${expr}:
.else
default:
.end if
.if (not self.duplicate)
  .if (then_block != "")
    ${then_block}\
  .end if
  .if (empty exiting_statement)
    break;
  .end if
.end if
