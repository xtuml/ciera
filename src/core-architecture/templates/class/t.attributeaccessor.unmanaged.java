.if (is_getter)
    public ${type_name} ${name}() {
        return ${self.attribute_name};
    }
.else
    public void ${name}(${type_name} ${self.attribute_name}) {
        this.${self.attribute_name} = ${self.attribute_name};
    }
.end if
