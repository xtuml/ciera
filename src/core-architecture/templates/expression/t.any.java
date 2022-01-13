${root_expression_body}.\
.if (not is_stream)
stream().\
.end if
findAny().orElse(${default_value})\
