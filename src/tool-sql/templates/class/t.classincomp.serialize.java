    public String serialize() throws XtumlException {
    	JSONObject attrs = new JSONObject();
${attribute_serializers}\
.if getstate        
        attrs.put( "current_state", statemachine.getCurrentState() );
.end if;       
    	return attrs.toString();
    }
