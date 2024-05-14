.if ( util_name != "" )
json.put("${attribute.base_name}", ${util_name}.serialize(${getter_name}()));
.else
json.put("${attribute.base_name}", ${getter_name}().serialize());
.end if
