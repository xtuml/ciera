package ${package};

${imports}

public class ${name} extends ${extends} {

    private static final int relNum = ${num};

    // supertype class
    private UniqueId ${supertype_class_name};

    // subtype class
    private UniqueId subtype;

    public ${name}( UniqueId ${supertype_class_name}, UniqueId subtype ) throws XtumlException {
        this.${supertype_class_name} = ${supertype_class_name};
        this.subtype = subtype;
    }

    @Override
    public UniqueId getSupertype() {
        return ${supertype_class_name};
    }

    @Override
    public UniqueId getSubtype() {
        return subtype;
    }

    @Override
    public int getNumber() {
        return relNum;
    }

    @Override
    public void delete() {
    }

}
