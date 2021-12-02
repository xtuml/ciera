    public static ${self.name} deserialize( Object o, ${compname} context ) {
    	try {
	        JSONObject jobj = new JSONObject((String)o);
	        ${self.name} set = new ${self.name}Impl();
	        JSONArray members = jobj.getJSONArray("${self.name}");
            int index = 0;
            String member = "";
            while (index < members.length()) {
              member = (String)members.get(index);
	          set.add(${self.class_name}Impl.deserialize( (Object)member, context ));
	          index++;
            }
	        return set;
    	}
    	catch(Exception ex ) { 
            System.out.printf( "${self.name} deserialize failed: %s \n", ex.toString() );
    	};
    	return (${self.name}) null;
    }