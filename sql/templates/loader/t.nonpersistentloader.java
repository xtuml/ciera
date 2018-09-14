.if ( "boolean" == self->type_name )
, false\
.elif ( "int" == self->type_name )
, 0\
.elif ( "double" == self->type_name )
, 0d\
.else
, new ${self.type_name}()\
.end if
