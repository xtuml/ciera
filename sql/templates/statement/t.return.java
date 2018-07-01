.if ( "" != oal )
${indent}// ${oal}
.end if
${prefix}\
.if ( "" == ret_expression_body )
${block_suffix}${indent}return;
.else
  .if ( returns_var )
${indent}_return_value = ${ret_expression_body};
${block_suffix}${indent}return _return_value;
  .else
${block_suffix}${indent}return ${ret_expression_body};
  .end if
.end if
