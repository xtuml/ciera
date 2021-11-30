    public String serialize() {
    	JSONObject attrs = new JSONObject();
${attribute_serializers}\
.if getstate        
        attrs.put( "m_current_state", statemachine.getCurrentState() );
.end if;       
    	return attrs.toString();
    }
