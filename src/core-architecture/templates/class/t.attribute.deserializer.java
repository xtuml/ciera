.if ( is_array )
inst.${setter_name}(ArrayUtil.deserialize(json.getString("${attribute.base_name}"), ${type_name}::deserialize));
.else
inst.${setter_name}(${type_name}.deserialize(json.getString("${attribute.base_name}")));
.end if
