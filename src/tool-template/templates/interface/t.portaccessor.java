    private ${port.name} ${port.name};
    public ${port.name} ${port.port_name}() {
        if ( null == ${port.name} )
.if not use_singleton
        	${port.name} = new ${port.name}( this, null );
.else
            ${port.name} = ${port.name}.Singleton();
.end if
        return ${port.name};
    }
