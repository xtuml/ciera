.if ( "" != oal )
${indent}// ${oal}
.end if
${prefix}\
.if ( "" == ret_expression_body )
${block_suffix}${indent}return;
.else
${block_suffix}${indent}return ${ret_expression_body};
.end if
