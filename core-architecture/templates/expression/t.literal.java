.if (is_string)
"${self.value}"\
.elif (is_char)
'${self.value}'\
.else
${self.value}\
.end if
