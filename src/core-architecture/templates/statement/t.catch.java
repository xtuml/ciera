.if (not exclude_comments)

/* ${self.actions} */
.end if
catch (\
.if (exception_name == "")
RuntimeException\
.else
${exception_name}\
.end if
 ${var_name}) ${catch_block}
