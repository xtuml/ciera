.if (is_getter)
${type_name} ${name}();
.else
void ${name}(${type_name} ${self.attribute_name});
.end if
