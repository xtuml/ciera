.if (statements == "")
{}
.else
  .if (is_else_if)
${statements}\
  .else
{
    .if (self.prefix != "")
    ${self.prefix}\
    .end if
    ${statements}\
    .if (self.suffix != "")
    ${self.suffix}\
    .end if
}
  .end if
.end if
