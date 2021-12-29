.if (input_type_primitive)
ModelType.castTo(${output_type_name}.class, ${root_expression_body})\
.else
${root_expression_body}.castTo(${output_type_name}.class)\
.end if
