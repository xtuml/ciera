    public ${type_name} get${capital_name}() throws XtumlException {
        checkLiving();
${referential_getters}
${attribute_processor}
        return ${name};
    }

    public void set${capital_name}( ${type_name} ${name} ) throws XtumlException {
        checkLiving();
${referential_setters}
        this.${name} = ${name};
    }
