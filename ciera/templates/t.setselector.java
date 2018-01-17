    public ${type_name} ${name}() throws XtumlException {
        return ${name}( null );
    }

    public ${type_name} ${name}( IWhere condition ) throws XtumlException {
.// one to other binary
.if ( 0 == selection_type )
        return null;
.// other to one binary
.elif ( 1 == selection_type )
        return null;
.// link to one assoc
.elif ( 2 == selection_type )
        return null;
.// link to other assoc
.elif ( 3 == selection_type )
        return null;
.// one to other assoc
.elif ( 4 == selection_type )
        return null;
.// one to link assoc
.elif ( 5 == selection_type )
        return null;
.// other to one assoc
.elif ( 6 == selection_type )
        return null;
.// other to link assoc
.elif ( 7 == selection_type )
        return null;
.// sub to super subsuper
.elif ( 8 == selection_type )
        return null;
.// super to sub subsuper
.elif ( 9 == selection_type )
        return null;
.else
        // Code generation error
.end if
    }
