.if ( "" != oal )
${indent}// ${oal}
.end if
.if ( "" == ret_expression_body )
${suffix}${indent}return;
.else
  .if ( returns_var )
${indent}_return_value = ${ret_expression_body};
${suffix}${indent}return _return_value;
  .else
${suffix}${indent}return ${ret_expression_body};
  .end if
.end if
