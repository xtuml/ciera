.if (is_passed_by_ref)
final LocalVariable<${type_name}> \
.else
  .if (var_readonly)
final \
  .end if
${type_name} \
.end if
