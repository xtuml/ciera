{
.if (self.prefix != "")
    ${self.prefix}\
.end if
.if (statements != "")
    ${statements}\
.end if
.if (self.suffix != "")
    ${self.suffix}\
.end if
}\
