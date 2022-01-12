    private ${self.name} ${self.name};
    public ${self.name} ${self.port_name}() {
        if ( null == ${self.name} )
.if not use_singleton
        	${self.name} = new ${self.name}( this, null );
.else
            ${self.name} = ${self.name}.Singleton();
.end if
        return ${self.name};
    }
