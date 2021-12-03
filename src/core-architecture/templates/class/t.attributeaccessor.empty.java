.if (is_getter)
public ${type_name} ${name}() {
    throw new EmptyInstanceException("Cannot get attribute of empty instance.");
}
.else
public void ${name}(${type_name} ${self.attribute_name}) {
    throw new EmptyInstanceException("Cannot set attribute of empty instance.");
}
.end if
