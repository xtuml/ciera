.if (self.declaration)
  .if (var_readonly)
final \
  .end if
${type_name} \
.end if
.if (finalization_num > -1)
_final${finalization_num}_\
.end if
${self.var_name}\
.// Java does not support local variable shadowing, so the variables must have
.// different names
.if (var.is_shadowing)
_${block_number}\
.end if
