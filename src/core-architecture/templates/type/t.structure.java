class ${self.name} implements Serializable {

    private static final long serialVersionUID = ${self.serial_version_id}L;

    // structure components
    ${component_decls}

    // constructors
    public ${self.name}() {
.if (component_initializers != "")
        ${component_initializers}\
.end if
    }

    // component accessors
    ${component_accessors}

    @Override
    public String toString() {
        return super.toString() + " ("${component_to_strings} + ")";
    }

}
