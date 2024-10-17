.if ( is_array )
inst.${setter_name}(ArrayUtil.deserialize(json.getString("${attribute.base_name}"), \
.if ( is_core )
${type_name}::deserialize\
.else
el -> {
    try {
        return ${type_name}.deserialize(el);
    } catch (XtumlException e) {
        return null;
    }
}\
.end if
));
.else
inst.${setter_name}(${type_name}.deserialize(json.getString("${attribute.base_name}")));
.end if
