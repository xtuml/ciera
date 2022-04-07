class ${self.name} implements Serializable {

    private static final long serialVersionUID = ${self.serial_version_id}l;

    private final ${self.type_reference} value;

    public ${self.name}() {
        value = ${default_value};
    }

    public ${self.name}(${self.type_reference} value) {
        this.value = value;
    }

    public ${self.type_reference} getValue() {
        return value;
    }

}
