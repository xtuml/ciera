${indent}// ${oal}
${indent}\
.if ( "" == ret_expression_body )
return;
.else
return ${ret_expression_body};
.end if
