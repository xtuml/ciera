.if ( "" == array_init_expression_body )
((${type_name})getSymbol( "${self.var_name}" ))\
.else
((${type_name})setSymbol( "${self.var_name}", new ${type_base_name}[${array_init_expression_body}+1] ))\
.end if
