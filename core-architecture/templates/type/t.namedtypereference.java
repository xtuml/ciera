class ${self.name} implements Serializable {

    private static final long serialVersionUID = ${self.serial_version_id}l;

    private final ${self.type_reference} value;

    \
.if (public_type)
public \
.end if
${self.name}() {
        value = ${default_value};
    }

    \
.if (public_type)
public \
.end if
${self.name}(${self.type_reference} value) {
        this.value = value;
    }

    \
.if (public_type)
public \
.end if
${self.type_reference} getValue() {
        return value;
    }

}
