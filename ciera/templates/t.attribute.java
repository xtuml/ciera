.if ( declaration )
    private ${type_name} ${attr_name};
.elif ( unique_id_initialize )
        ${attr_name} = new ${type_name}();
.end if
