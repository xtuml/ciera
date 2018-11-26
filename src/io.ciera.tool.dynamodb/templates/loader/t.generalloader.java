.if ( "boolean" == self.type_name )
false\
.elif ( "int" == self.type_name )
0\
.elif ( "double" == self.type_name )
0d\
.else
  .if ( self.load_value )
${self.type_name}.deserialize(values.get("${self.attr_name}"))\
  .else
new ${self.type_name}()\
  .end if
.end if
