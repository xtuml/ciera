${root_expression_body}.\
.if (not is_stream)
stream().\
.end if
.if (self.strict)
collect(StreamUtil.findOnly())\
.else
findAny()\
.end if
..orElse(${default_value})\
