.if ( "" != oal )
${indent}// ${oal}
.end if
.if ( "" == ret_expression_body )
  .if ( handle_locals )
${indent}popSymbolTable();
  .end if
${indent}return;
.else
  .if ( returns_var )
${indent}_return_value = ${ret_expression_body};
  .end if
  .if ( handle_locals )
${indent}popSymbolTable();
  .end if
  .if ( returns_var )
${indent}return _return_value;
  .else
${indent}return ${ret_expression_body};
  .end if
.end if
