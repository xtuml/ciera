    public static ${self.name} deserialize( Object o, ${compname} context ) {
    	try {
	        JSONObject jobj = new JSONObject((String)o);
.if getstate	        
	        ${self.name} inst = ${self.name}Impl.create( context${attribute_deserializers}, (int)jobj.get("m_current_state" ) );
.else
            ${self.name} inst = ${self.name}Impl.create( context${attribute_deserializers} );
.end if
	        return inst;
    	}
    	catch(Exception ex ) { 
            System.out.printf( "Employee deserialize failed: %s \n", ex.toString() );
    	};
    	return (${self.name}) null;
    }
