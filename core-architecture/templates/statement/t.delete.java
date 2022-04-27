.if (is_many)
${inst_expr}.forEach(ObjectInstance::delete);
.else
${inst_expr}.delete();
.end if
