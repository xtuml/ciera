${root_expression_body}.\
.if (self.strict)
collect(StreamUtil.findOnly())\
.else
findAny()\
.end if
..orElse(${default_value})\
