.if ( is_else_if )
${statements}\
.else
{
${self.prefix}${statements}\
  .if ( include_suffix )
${self.suffix}\
  .end if
${indent}}
.end if
