.if (public_type)
public \
.end if
${self.type_reference} get$c{self.name}() {
    return ${self.name};
}

.if (public_type)
public \
.end if
void set$c{self.name}(${self.type_reference} ${self.name}) {
    this.${self.name} = ${self.name};
}

