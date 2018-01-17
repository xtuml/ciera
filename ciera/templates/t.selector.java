    public ${type_name} ${name}() throws XtumlException {
        return ${name}( null );
    }

    public ${type_name} ${name}( IWhere condition ) throws XtumlException {
.if ( 0 == selection_type )
.elif ( 1 == selection_type )
.elif ( 2 == selection_type )
.elif ( 3 == selection_type )
.elif ( 4 == selection_type )
.elif ( 5 == selection_type )
.elif ( 6 == selection_type )
.elif ( 7 == selection_type )
.elif ( 8 == selection_type )
.elif ( 9 == selection_type )
.else
        // Code generation error
.end if
    }
