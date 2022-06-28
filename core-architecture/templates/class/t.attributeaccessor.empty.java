.if (is_getter)
@Override
${type_name} ${name}() {
    throw new EmptyInstanceException("Cannot get attribute '${self.attribute_name}' of empty instance", null, this);
}
.else
@Override
void ${name}(${type_name} ${self.attribute_name}) {
    throw new EmptyInstanceException("Cannot set attribute '${self.attribute_name}' of empty instance", null, this);
}
.end if
