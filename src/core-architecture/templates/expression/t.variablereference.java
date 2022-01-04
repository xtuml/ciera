.if (self.declaration)
  .if (var_readonly)
final \
  .end if
${type_name} \
.end if
.if (finalization_num > -1)
_final${finalization_num}_\
.end if
${var_name}\
