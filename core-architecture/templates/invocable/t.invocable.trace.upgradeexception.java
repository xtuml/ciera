.if (convert)
if (!(${exception_name} instanceof ActionException)) {
    ${exception_name} = new ActionException(${exception_name});
}
((ActionException) ${exception_name})\
.else
${exception_name}\
.end if
..updateStack("${original_parent_name}", "${original_body_name}", \
.if (self.original_filename != "")
"${self.original_filename}"\
.else
"<Unknown>"\
.end if
, _lineNumber);
