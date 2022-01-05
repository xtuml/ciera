public static ${type_name} ${self.name}(${self.comp_name} domain\
.if (parameter_list != "")
, ${parameter_list}\
.end if
) {
    \
.if (type_name != "void")
return \
.end if
new DynamicActionHome<${type_name2}, ${self.comp_name}>(domain) {
        @Override
        public ${type_name2} runAction() \
.if (type_name != "void")
${body}
.else
{
    ${body}
    return null;
}
.end if
    }.runAction();
}

