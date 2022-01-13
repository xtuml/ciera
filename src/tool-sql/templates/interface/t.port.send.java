    @Override 
    public void send(IMessage message) throws XtumlException {
    	String msgname = message.getName();
     	String payload = (String)message.getParms().toString();
    	SpringMsg springmsg = new SpringMsg ( msgname, payload );
        String topic = "/topic/${topic}/";
.if (discriminant != "" )
        JSONObject jobj = new JSONObject(payload);
        String subtopic = jobj.getString("${discriminant}");
        topic = topic + subtopic;
.end if 
    	this.template.convertAndSend( topic, springmsg );
    }
