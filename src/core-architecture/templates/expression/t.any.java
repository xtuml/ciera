${root_expression_body}.\
.if (not is_stream)
stream().\
.end if
findAny()\
.if (self.strict)
..orElseThrow()\
.else
..orElse(${default_value})\
.end if
