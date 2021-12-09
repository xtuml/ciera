    public String serialize() {
    	JSONObject attrs = new JSONObject();
${attribute_serializers}\
.if getstate        
        attrs.put( "m_Current_state", statemachine.getCurrentState() );
.end if;       
    	return attrs.toString();
    }
