.if ( is_string )
new XtumlString( "${self.value}" )\
.else
${self.value}\
.end if
