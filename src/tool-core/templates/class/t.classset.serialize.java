    public String serialize() throws XtumlException {
		JSONObject setstr = new JSONObject();
		JSONArray setarr = new JSONArray();
		setstr.put("${self.name}", setarr);
		String memberstr;
		List<${self.class_name}> contents = elements();
		for ( ${membername} member : contents ) {
            memberstr = member.serialize();
            setarr.put(memberstr);
        }
		return setstr.toString();
    }
    