.if (is_getter)
public ${type_name} ${name}();
.else
public void ${name}(${type_name} ${self.attribute_name});
.end if
