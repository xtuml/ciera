.if (include_comments)
/* ${self.actions} */
.end if;
catch (\
.if (self.exception_name == "")
RuntimeException\
.else
${self.exception_name}\
.end if
 _e) ${catch_block}\
